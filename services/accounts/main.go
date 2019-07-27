package main

import (
	"context"
	"errors"
	"log"
	pb "micro-drive/services/accounts/proto"
	"time"

	"go.mongodb.org/mongo-driver/bson/primitive"

	"go.mongodb.org/mongo-driver/bson"

	"github.com/dgrijalva/jwt-go"
	"github.com/micro/cli"
	"github.com/micro/go-micro"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"golang.org/x/crypto/bcrypt"
)

type accountsService struct {
	mongoClient *mongo.Client
}

func (a *accountsService) CreateUser(ctx context.Context, req *pb.CreateUserRequest, res *pb.CreateAccountResponse) error {
	ctx, cancelFunc := context.WithTimeout(context.Background(), time.Second)
	defer cancelFunc()

	account := req.Account
	coll := a.mongoClient.Database("microDrive").Collection("accounts")

	var result struct{}
	err := coll.FindOne(ctx, bson.M{"username": account.Username, "role": "user"}).Decode(&result)
	if err == nil {
		// FindOne return document
		return errors.New("User already exists")
	}

	hash, err := bcrypt.GenerateFromPassword([]byte(account.Password), 12)
	if err != nil {
		return err
	}

	doc := bson.M{
		"username":  account.Username,
		"firstName": account.FirstName,
		"lastName":  account.LastName,
		"password":  hash,
		"role":      "user",
		"createdAt": time.Now(),
	}

	_, err = coll.InsertOne(ctx, doc)
	if err != nil {
		return err
	}

	return nil
}

func (a *accountsService) CreateDriver(ctx context.Context, req *pb.CreateDriverRequest, res *pb.CreateAccountResponse) error {
	ctx, cancelFunc := context.WithTimeout(context.Background(), time.Second)
	defer cancelFunc()

	account := req.Account
	coll := a.mongoClient.Database("microDrive").Collection("accounts")
	var result struct{}
	err := coll.FindOne(ctx, bson.M{"username": account.Username, "role": "driver"}).Decode(&result)
	if err == nil {
		// FindOne return document
		return errors.New("Driver already exists")
	}

	hash, err := bcrypt.GenerateFromPassword([]byte(account.Password), 12)
	if err != nil {
		return err
	}

	doc := bson.M{
		"username":  account.Username,
		"firstName": account.FirstName,
		"lastName":  account.LastName,
		"password":  hash,
		"role":      "driver",
		"car":       bson.M{"mark": req.Car.Mark, "number": req.Car.Number},
		"createdAt": time.Now(),
	}

	_, err = coll.InsertOne(ctx, doc)
	if err != nil {
		return err
	}

	return nil
}

type account struct {
	FirstName string `bson:"firstName"`
	LastName  string `bson:"lastName"`
	Car       car    `bson:"car"`
}

type car struct {
	Mark   string `bson:"mark"`
	Number string `bson:"number"`
}

func (a *accountsService) GetDriver(ctx context.Context, req *pb.GetDriverRequest, res *pb.Driver) error {
	ctx, cancelFunc := context.WithTimeout(context.Background(), time.Second)
	defer cancelFunc()
	coll := a.mongoClient.Database("microDrive").Collection("accounts")

	var result account
	id, err := primitive.ObjectIDFromHex(req.Id)
	if err != nil {
		return err
	}
	err = coll.FindOne(ctx, bson.M{"_id": id, "role": "driver"}).Decode(&result)
	if err != nil {
		// Driver not found
		return err
	}

	res.Id = req.Id
	res.FirstName = result.FirstName
	res.LastName = result.LastName
	res.Car = &pb.Car{Mark: result.Car.Mark, Number: result.Car.Number}

	return nil
}

func (a *accountsService) GetToken(ctx context.Context, req *pb.GetTokenRequest, res *pb.GetTokenResponse) error {
	ctx, cancelFunc := context.WithTimeout(context.Background(), time.Second)
	defer cancelFunc()
	coll := a.mongoClient.Database("microDrive").Collection("accounts")

	var result struct {
		ID       primitive.ObjectID `bson:"_id"`
		Password []byte             `bson:"password"`
	}
	err := coll.FindOne(ctx, bson.M{"username": req.Username}).Decode(&result)

	if err != nil {
		return err
	}

	err = bcrypt.CompareHashAndPassword(result.Password, []byte(req.Password))
	if err != nil {
		return errors.New("Username or password are wrong")
	}

	token := jwt.NewWithClaims(jwt.SigningMethodHS256, jwt.MapClaims{
		"sub": result.ID.Hex(),
		"exp": time.Now().Add(time.Hour).Unix(),
	})

	tokenString, err := token.SignedString([]byte("secret"))
	if err != nil {
		return err
	}

	res.Token = tokenString

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
