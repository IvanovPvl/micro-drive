// Code generated by protoc-gen-go. DO NOT EDIT.
// source: drivers.proto

package drivers

import (
	fmt "fmt"
	proto "github.com/golang/protobuf/proto"
	math "math"
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

type GetFreeDriverRequest struct {
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *GetFreeDriverRequest) Reset()         { *m = GetFreeDriverRequest{} }
func (m *GetFreeDriverRequest) String() string { return proto.CompactTextString(m) }
func (*GetFreeDriverRequest) ProtoMessage()    {}
func (*GetFreeDriverRequest) Descriptor() ([]byte, []int) {
	return fileDescriptor_81dfd49b5b303fb4, []int{0}
}

func (m *GetFreeDriverRequest) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_GetFreeDriverRequest.Unmarshal(m, b)
}
func (m *GetFreeDriverRequest) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_GetFreeDriverRequest.Marshal(b, m, deterministic)
}
func (m *GetFreeDriverRequest) XXX_Merge(src proto.Message) {
	xxx_messageInfo_GetFreeDriverRequest.Merge(m, src)
}
func (m *GetFreeDriverRequest) XXX_Size() int {
	return xxx_messageInfo_GetFreeDriverRequest.Size(m)
}
func (m *GetFreeDriverRequest) XXX_DiscardUnknown() {
	xxx_messageInfo_GetFreeDriverRequest.DiscardUnknown(m)
}

var xxx_messageInfo_GetFreeDriverRequest proto.InternalMessageInfo

type GetFreeDriverResponse struct {
	DriverId             string   `protobuf:"bytes,1,opt,name=driverId,proto3" json:"driverId,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *GetFreeDriverResponse) Reset()         { *m = GetFreeDriverResponse{} }
func (m *GetFreeDriverResponse) String() string { return proto.CompactTextString(m) }
func (*GetFreeDriverResponse) ProtoMessage()    {}
func (*GetFreeDriverResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_81dfd49b5b303fb4, []int{1}
}

func (m *GetFreeDriverResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_GetFreeDriverResponse.Unmarshal(m, b)
}
func (m *GetFreeDriverResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_GetFreeDriverResponse.Marshal(b, m, deterministic)
}
func (m *GetFreeDriverResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_GetFreeDriverResponse.Merge(m, src)
}
func (m *GetFreeDriverResponse) XXX_Size() int {
	return xxx_messageInfo_GetFreeDriverResponse.Size(m)
}
func (m *GetFreeDriverResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_GetFreeDriverResponse.DiscardUnknown(m)
}

var xxx_messageInfo_GetFreeDriverResponse proto.InternalMessageInfo

func (m *GetFreeDriverResponse) GetDriverId() string {
	if m != nil {
		return m.DriverId
	}
	return ""
}

type ReleaseDriverRequest struct {
	DriverId             string   `protobuf:"bytes,1,opt,name=driverId,proto3" json:"driverId,omitempty"`
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *ReleaseDriverRequest) Reset()         { *m = ReleaseDriverRequest{} }
func (m *ReleaseDriverRequest) String() string { return proto.CompactTextString(m) }
func (*ReleaseDriverRequest) ProtoMessage()    {}
func (*ReleaseDriverRequest) Descriptor() ([]byte, []int) {
	return fileDescriptor_81dfd49b5b303fb4, []int{2}
}

func (m *ReleaseDriverRequest) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_ReleaseDriverRequest.Unmarshal(m, b)
}
func (m *ReleaseDriverRequest) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_ReleaseDriverRequest.Marshal(b, m, deterministic)
}
func (m *ReleaseDriverRequest) XXX_Merge(src proto.Message) {
	xxx_messageInfo_ReleaseDriverRequest.Merge(m, src)
}
func (m *ReleaseDriverRequest) XXX_Size() int {
	return xxx_messageInfo_ReleaseDriverRequest.Size(m)
}
func (m *ReleaseDriverRequest) XXX_DiscardUnknown() {
	xxx_messageInfo_ReleaseDriverRequest.DiscardUnknown(m)
}

var xxx_messageInfo_ReleaseDriverRequest proto.InternalMessageInfo

func (m *ReleaseDriverRequest) GetDriverId() string {
	if m != nil {
		return m.DriverId
	}
	return ""
}

type ReleaseDriverResponse struct {
	XXX_NoUnkeyedLiteral struct{} `json:"-"`
	XXX_unrecognized     []byte   `json:"-"`
	XXX_sizecache        int32    `json:"-"`
}

func (m *ReleaseDriverResponse) Reset()         { *m = ReleaseDriverResponse{} }
func (m *ReleaseDriverResponse) String() string { return proto.CompactTextString(m) }
func (*ReleaseDriverResponse) ProtoMessage()    {}
func (*ReleaseDriverResponse) Descriptor() ([]byte, []int) {
	return fileDescriptor_81dfd49b5b303fb4, []int{3}
}

func (m *ReleaseDriverResponse) XXX_Unmarshal(b []byte) error {
	return xxx_messageInfo_ReleaseDriverResponse.Unmarshal(m, b)
}
func (m *ReleaseDriverResponse) XXX_Marshal(b []byte, deterministic bool) ([]byte, error) {
	return xxx_messageInfo_ReleaseDriverResponse.Marshal(b, m, deterministic)
}
func (m *ReleaseDriverResponse) XXX_Merge(src proto.Message) {
	xxx_messageInfo_ReleaseDriverResponse.Merge(m, src)
}
func (m *ReleaseDriverResponse) XXX_Size() int {
	return xxx_messageInfo_ReleaseDriverResponse.Size(m)
}
func (m *ReleaseDriverResponse) XXX_DiscardUnknown() {
	xxx_messageInfo_ReleaseDriverResponse.DiscardUnknown(m)
}

var xxx_messageInfo_ReleaseDriverResponse proto.InternalMessageInfo

func init() {
	proto.RegisterType((*GetFreeDriverRequest)(nil), "GetFreeDriverRequest")
	proto.RegisterType((*GetFreeDriverResponse)(nil), "GetFreeDriverResponse")
	proto.RegisterType((*ReleaseDriverRequest)(nil), "ReleaseDriverRequest")
	proto.RegisterType((*ReleaseDriverResponse)(nil), "ReleaseDriverResponse")
}

func init() { proto.RegisterFile("drivers.proto", fileDescriptor_81dfd49b5b303fb4) }

var fileDescriptor_81dfd49b5b303fb4 = []byte{
	// 157 bytes of a gzipped FileDescriptorProto
	0x1f, 0x8b, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00, 0x02, 0xff, 0xe2, 0xe2, 0x4d, 0x29, 0xca, 0x2c,
	0x4b, 0x2d, 0x2a, 0xd6, 0x2b, 0x28, 0xca, 0x2f, 0xc9, 0x57, 0x12, 0xe3, 0x12, 0x71, 0x4f, 0x2d,
	0x71, 0x2b, 0x4a, 0x4d, 0x75, 0x01, 0x8b, 0x07, 0xa5, 0x16, 0x96, 0xa6, 0x16, 0x97, 0x28, 0x19,
	0x73, 0x89, 0xa2, 0x89, 0x17, 0x17, 0xe4, 0xe7, 0x15, 0xa7, 0x0a, 0x49, 0x71, 0x71, 0x40, 0x4c,
	0xf0, 0x4c, 0x91, 0x60, 0x54, 0x60, 0xd4, 0xe0, 0x0c, 0x82, 0xf3, 0x95, 0x8c, 0xb8, 0x44, 0x82,
	0x52, 0x73, 0x52, 0x13, 0x8b, 0x51, 0x0d, 0xc3, 0xab, 0x47, 0x9c, 0x4b, 0x14, 0x4d, 0x0f, 0xc4,
	0x22, 0xa3, 0x4e, 0x46, 0x2e, 0x76, 0x88, 0x50, 0xb1, 0x90, 0x1d, 0x17, 0x2f, 0x8a, 0x6b, 0x84,
	0x44, 0xf5, 0xb0, 0xb9, 0x5a, 0x4a, 0x4c, 0x0f, 0xbb, 0xa3, 0xed, 0xb8, 0x78, 0x51, 0x2c, 0x11,
	0x12, 0xd5, 0xc3, 0xe6, 0x50, 0x29, 0x31, 0x3d, 0xac, 0x6e, 0x49, 0x62, 0x03, 0x07, 0x96, 0x31,
	0x20, 0x00, 0x00, 0xff, 0xff, 0xfd, 0x2a, 0xd3, 0x09, 0x3d, 0x01, 0x00, 0x00,
}