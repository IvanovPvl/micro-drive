package main

import (
	"context"
	"log"
	pb "micro-drive/services/accounts/proto"
	"time"

	"github.com/micro/cli"
	"github.com/micro/go-micro"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
)

type accountsService struct {
	mongoClient *mongo.Client
}

func (a *accountsService) CreateAccount(ctx context.Context, req *pb.CreateAccountRequest, res *pb.CreateAccountResponse) error {
	return nil
}

func main() {
	service := micro.NewService(
		micro.Name("micro-drive.api.accounts"),
		micro.Flags(
			cli.StringFlag{
				Name:  "accounts-storage-uri",
				Usage: "Accounts storage URI",
			},
		),
	)

	var storageURI string
	service.Init(
		micro.Action(func(c *cli.Context) {
			storageURI = c.String("accounts-storage-uri")
		}),
	)

	ctx, cancelFunc := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancelFunc()

	client, err := mongo.Connect(ctx, options.Client().ApplyURI(storageURI))
	if err != nil {
		log.Fatalf("Could not connect to MongoDB: %v", err)
	}

	pb.RegisterAccountsHandler(service.Server(), &accountsService{mongoClient: client})
	if err := service.Run(); err != nil {
		log.Fatalf("Failed to start service: %v", err)
	}
}
