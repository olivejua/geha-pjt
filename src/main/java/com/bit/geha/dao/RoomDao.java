package com.bit.geha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.bit.geha.dto.FacilityDto;
import com.bit.geha.dto.FileDto;
import com.bit.geha.dto.GuestHouseDto;
import com.bit.geha.dto.LikeDto;
import com.bit.geha.dto.RoomDto;

@Mapper
public interface RoomDao {
	
	List<GuestHouseDto> selectGuestHouse();
	
	GuestHouseDto gehaInfo(int guestHouseCode);
	List<RoomDto> roomInfo(String bookingStart,String bookingEnd,int bookingNumber,int guestHouseCode);
	List<FacilityDto> facilityInfo(int guestHouseCode);
	
	List<FileDto> gehaImg(int guestHouseCode);
	
	void addlike(LikeDto likeDto);

}
