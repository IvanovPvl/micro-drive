package tomtom

import (
	"bytes"
	"encoding/json"
	"io"
	"net/http"
	"net/url"
	"path"
)

// Client manages communication with the API
type Client struct {
	// Client used to send and receive http requests
	client *http.Client

	// Base URL for API requests
	BaseURL *url.URL

	// ToTom API key
	Key string

	// Version of TomTom API
	APIVersion string

	// Services used for talking to different parts of the API
	Routing *RoutingService
}

const baseURL = "https://api.tomtom.com"

// NewClient returns a new API client.
// Camunda base url can be provided in baseURL.
// If a nil httpClient is provided, http.DefaultClient will be used.
func NewClient(apiKey, apiVersion string, httpClient *http.Client) *Client {
	if httpClient == nil {
		httpClient = http.DefaultClient
	}

	parsedURL, _ := url.Parse(baseURL)

	c := &Client{
		client:     httpClient,
		BaseURL:    parsedURL,
		Key:        apiKey,
		APIVersion: apiVersion,
	}
	c.Routing = &RoutingService{client: c}

	return c
}

// NewRequest creates an API request. A relative URL can be provided in urlStr,
// in which case it is resolved relative to the BaseURL of the Client.
func (c *Client) NewRequest(method, urlStr string, body interface{}) (*http.Request, error) {
	u, _ := url.Parse(c.BaseURL.String())
	u.Path = path.Join(c.BaseURL.Path, urlStr)

	var buf io.ReadWriter
	if body != nil {
		buf = new(bytes.Buffer)
		err := json.NewEncoder(buf).Encode(body)
		if err != nil {
			return nil, err
		}
	}

	req, err := http.NewRequest(method, u.String(), buf)
	if err != nil {
		return nil, err
	}

	if method == "POST" {
		req.Header.Set("Content-Type", "application/json")
	}

	return req, nil
}

// Response is an API response.
// This wraps the standard http.Response.
type Response struct {
	*http.Response
}

// newResponse creates a new Response for the provided http.Response.
func newResponse(r *http.Response) *Response {
	response := &Response{Response: r}
	return response
}

// Do sends an API request and returns the API response.  The API response is
// JSON decoded and stored in the value pointed to by v, or returned as an
// error if an API error has occurred.  If v implements the io.Writer
// interface, the raw response body will be written to v, without attempting to
// first decode it.
func (c *Client) Do(req *http.Request, v interface{}) (*Response, error) {
	resp, err := c.client.Do(req)
	if err != nil {
		return nil, err
	}

	defer resp.Body.Close()

	response := newResponse(resp)

	if v != nil {
		if w, ok := v.(io.Writer); ok {
			io.Copy(w, resp.Body)
		} else {
			err := json.NewDecoder(resp.Body).Decode(v)
			if err == io.EOF {
				err = nil
			}
		}
	}

	return response, err
}
