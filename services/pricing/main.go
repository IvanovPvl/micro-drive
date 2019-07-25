package main

import (
	"context"
	pb "micro-drive/services/pricing/proto"

	"github.com/micro/go-micro"
)

const priceForKm = 1.3

type pricing struct{}

func (p *pricing) GetPrice(ctx context.Context, req *pb.Request, res *pb.Response) error {
	price := priceForKm * (float64)(req.LengthInMeters)
	res.Price = price
	return nil
}

func main() {
	service := micro.NewService(
		micro.Name("micro-drive.api.pricing"),
	)

	service.Init()

	pb.RegisterPricingHandler(service.Server(), &pricing{})
	service.Run()
}
