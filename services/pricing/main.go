package main

import (
	"context"
	"log"
	pb "micro-drive/services/pricing/proto"

	"github.com/micro/cli"

	"github.com/micro/go-micro"
)

type pricingService struct {
	priceForKm float64
}

func (p *pricingService) GetPrice(ctx context.Context, req *pb.Request, res *pb.Response) error {
	price := p.priceForKm * (float64)(req.LengthInMeters)
	res.Price = price
	return nil
}

func main() {
	var priceForKm float64

	service := micro.NewService(
		micro.Name("micro-drive.api.pricing"),
		micro.Flags(
			cli.Float64Flag{
				Name: "priceForKm",
			},
		),
	)

	service.Init(
		micro.Action(func(c *cli.Context) {
			priceForKm = c.Float64("priceForKm")
		}),
	)

	pb.RegisterPricingHandler(service.Server(), &pricingService{priceForKm: priceForKm})
	if err := service.Run(); err != nil {
		log.Fatalf("Failed to start service: %v", err)
	}
}
