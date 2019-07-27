// Code generated by protoc-gen-micro. DO NOT EDIT.
// source: routing.proto

package routing

import (
	fmt "fmt"
	proto "github.com/golang/protobuf/proto"
	math "math"
)

import (
	context "context"
	client "github.com/micro/go-micro/client"
	server "github.com/micro/go-micro/server"
)

// Reference imports to suppress errors if they are not otherwise used.
var _ = proto.Marshal
var _ = fmt.Errorf
var _ = math.Inf

// This is a compile-time assertion to ensure that this generated file
// is compatible with the proto package it is being compiled against.
// A compilation error at this line likely means your copy of the
// proto package needs to be updated.
const _ = proto.ProtoPackageIsVersion3 // please upgrade the proto package

// Reference imports to suppress errors if they are not otherwise used.
var _ context.Context
var _ client.Option
var _ server.Option

// Client API for Routing service

type RoutingService interface {
	GetRoute(ctx context.Context, in *GetRouteRequest, opts ...client.CallOption) (*Route, error)
}

type routingService struct {
	c    client.Client
	name string
}

func NewRoutingService(name string, c client.Client) RoutingService {
	if c == nil {
		c = client.NewClient()
	}
	if len(name) == 0 {
		name = "routing"
	}
	return &routingService{
		c:    c,
		name: name,
	}
}

func (c *routingService) GetRoute(ctx context.Context, in *GetRouteRequest, opts ...client.CallOption) (*Route, error) {
	req := c.c.NewRequest(c.name, "Routing.GetRoute", in)
	out := new(Route)
	err := c.c.Call(ctx, req, out, opts...)
	if err != nil {
		return nil, err
	}
	return out, nil
}

// Server API for Routing service

type RoutingHandler interface {
	GetRoute(context.Context, *GetRouteRequest, *Route) error
}

func RegisterRoutingHandler(s server.Server, hdlr RoutingHandler, opts ...server.HandlerOption) error {
	type routing interface {
		GetRoute(ctx context.Context, in *GetRouteRequest, out *Route) error
	}
	type Routing struct {
		routing
	}
	h := &routingHandler{hdlr}
	return s.Handle(s.NewHandler(&Routing{h}, opts...))
}

type routingHandler struct {
	RoutingHandler
}

func (h *routingHandler) GetRoute(ctx context.Context, in *GetRouteRequest, out *Route) error {
	return h.RoutingHandler.GetRoute(ctx, in, out)
}