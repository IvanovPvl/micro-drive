package main

import (
	"context"
	"log"
	pb "micro-drive/services/drivers/proto"

	"github.com/go-redis/redis"
	"github.com/micro/go-micro"
)

type drivers struct {
	redisClient *redis.Client
}

const freeDrivers = "free_drivers"

func (d *drivers) GetFreeDriver(ctx context.Context, req *pb.GetFreeDriverRequest, res *pb.GetFreeDriverResponse) error {
	result, err := d.redisClient.SPop(freeDrivers).Result()
	if err != nil {
		return err
	}

	// TODO: what happens if set is empty?
	res.DriverId = result
	return nil
}

func (d *drivers) ReleaseDriver(ctx context.Context, req *pb.ReleaseDriverRequest, res *pb.ReleaseDriverResponse) error {
	_, err := d.redisClient.SAdd(freeDrivers, req.DriverId).Result()
	if err != nil {
		return err
	}

	return nil
}

func main() {
	client := redis.NewClient(&redis.Options{})

	service := micro.NewService(
		micro.Name("micro-drive.api.drivers"),
	)

	service.Init()

	pb.RegisterDriversHandler(service.Server(), &drivers{redisClient: client})
	if err := service.Run(); err != nil {
		log.Fatalf("Failed to start service: %v", err)
	}
}
