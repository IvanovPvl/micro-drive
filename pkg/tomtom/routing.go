package tomtom

import (
	"fmt"
)

type RoutingService struct {
	client *Client
}

type CalculateRouteResponse struct {
	Copyright     string
	FormatVersion string
	Privacy       string
	Routes        []Route
}

type Route struct {
	Summary  Summary
	Legs     []Leg
	Sections []Section
}

type Leg struct {
	Summary Summary
	Points  []Point
}

type Summary struct {
	LengthInMeters        uint32
	TravelTimeInSeconds   uint32
	TrafficDelayInSeconds uint32
	DepartureTime         string
}

type Point struct {
	Latitude  float32
	Longitude float32
}

type Section struct {
	StartPointIndex uint32
	EndPointIndex   uint32
	SectionType     string
	TravelMode      string
}

func (r *RoutingService) CalculateRoute(from, to *Point) (*CalculateRouteResponse, *Response, error) {
	locations := fmt.Sprintf("%f,%f:%f,%f", from.Latitude, from.Longitude, to.Latitude, to.Longitude)
	url := fmt.Sprintf(
		"/routing/%s/%s/%s/json",
		r.client.APIVersion,
		"calculateRoute",
		locations,
	)

	req, err := r.client.NewRequest("GET", url, nil)
	if err != nil {
		return nil, nil, err
	}

	q := req.URL.Query()
	q.Add("travelMode", "car")
	q.Add("key", r.client.Key)
	req.URL.RawQuery = q.Encode()

	var calcRes = new(CalculateRouteResponse)
	res, err := r.client.Do(req, calcRes)
	if err != nil {
		return nil, res, err
	}

	return calcRes, res, nil
}
