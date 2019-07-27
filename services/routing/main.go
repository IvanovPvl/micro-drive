package main

import (
	"context"
	"errors"
	"log"
	"micro-drive/pkg/tomtom"
	pb "micro-drive/services/routing/proto"

	"github.com/micro/cli"
	"github.com/micro/go-micro"
)

type routingService struct {
	tomtomClient *tomtom.Client
}

func (r *routingService) GetRoute(ctx context.Context, req *pb.GetRouteRequest, res *pb.Route) error {
	from := &tomtom.Point{
		Latitude:  req.From.Latitude,
		Longitude: req.From.Longitude,
	}

	to := &tomtom.Point{
		Latitude:  req.To.Latitude,
		Longitude: req.To.Longitude,
	}

	routeRes, _, err := r.tomtomClient.Routing.CalculateRoute(from, to)
	if err != nil {
		return err
	}

	if len(routeRes.Routes) == 0 {
		return errors.New("Find no routes")
	}

	*res = *convertToRoute(routeRes)
	return nil
}

func convertToRoute(tomtomRoute *tomtom.CalculateRouteResponse) *pb.Route {
	route := tomtomRoute.Routes[0]
	summary := route.Summary
	leg := route.Legs[0]
	res := &pb.Route{
		LengthInMeters:        summary.LengthInMeters,
		TravelTimeInSeconds:   summary.TravelTimeInSeconds,
		TrafficDelayInSeconds: summary.TrafficDelayInSeconds,
	}

	points := make([]*pb.Point, len(leg.Points))
	for i, p := range leg.Points {
		points[i] = &pb.Point{
			Latitude:  p.Latitude,
			Longitude: p.Longitude,
		}
	}
	res.Points = points
	return res
}

func main() {
	var tomtomAPIKey string

	service := micro.NewService(
		micro.Name("micro-drive.api.routing"),
		micro.Flags(
			cli.StringFlag{
				Name: "tomtomApiKey",
			},
		),
	)

	service.Init(
		micro.Action(func(c *cli.Context) {
			tomtomAPIKey = c.String("tomtomApiKey")
		}),
	)

	tomtomClient := tomtom.NewClient(tomtomAPIKey, "1", nil)

	pb.RegisterRoutingHandler(service.Server(), &routingService{tomtomClient: tomtomClient})
	if err := service.Run(); err != nil {
		log.Fatalf("Failed to start service: %v", err)
	}
}
