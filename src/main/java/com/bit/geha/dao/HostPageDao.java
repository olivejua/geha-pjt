package com.bit.geha.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bit.geha.dto.BoardCriteria;
import com.bit.geha.dto.BookingDto;
import com.bit.geha.dto.FacilityDto;
import com.bit.geha.dto.FileDto;
import com.bit.geha.dto.GuestHouseDto;
import com.bit.geha.dto.RejectDto;
import com.bit.geha.dto.ReviewDto;
import com.bit.geha.dto.RoomDto;

@Mapper
public interface HostPageDao {
	//내 게스트하우스
	@Select("SELECT * FROM guestHouse_tb WHERE memberCode=#{memberCode}")
	public List<GuestHouseDto> getGuestHouseList(int memberCode);
	
	@Select("SELECT guestHouseCode FROM reject_tb WHERE memberCode=#{memberCode}")
	public List<Integer> getIsRejectList(int memberCode);
	
	@Select("SELECT * FROM reject_tb WHERE guestHouseCode=#{guestHouseCode}")
	public List<RejectDto> getRejectListByGuestHouseCode(int guestHouseCode);
	
	//게스트하우스 등록하기
	public void addGuestHouse(GuestHouseDto guestHouseDto);
	public void addRooms(List<RoomDto> roomDtoList);
	public void addFacilities(Map<String, Object> facilityMap);
	public void addFiles(List<FileDto> files);
	
	//게스트하우스 수정하기
	@Select("SELECT * FROM guestHouse_tb WHERE guestHouseCode=#{guestHouseCode}")
	public GuestHouseDto getGuestHouse(int guestHouseCode);
	
	@Select("SELECT * FROM room_tb WHERE guestHouseCode=#{guestHouseCode}")
	public List<RoomDto> getRooms(int guestHouseCode);
	
	@Select("SELECT roomCode FROM room_tb WHERE guestHouseCode=#{guestHouseCode}")
	public List<Integer> getRoomCodes(int guestHouseCode);
	
	@Select("SELECT facilityCode FROM guestHouse_has_facility_tb WHERE guestHouseCode=#{guestHouseCode}")
	public List<Integer> getFacilities(int guestHouseCode);
	
	@Select("SELECT * FROM file_tb WHERE guestHouseCode=#{guestHouseCode} AND roomCode=#{roomCode}")
	public List<FileDto> getImgs(int guestHouseCode, int roomCode);
	
	public void modifyGuestHouse(GuestHouseDto guestHouseDto);
	public void modifyMainImage(FileDto img);
	public void modifyRoom(RoomDto roomDto);
	
	
	//게스트하우스 삭제하기
	@Delete("DELETE FROM file_tb WHERE guestHouseCode=#{guestHouseCode} AND roomCode=#{roomCode}")
	public void deleteImgs(int guestHouseCode, int roomCode);
	@Delete("DELETE FROM guestHouse_has_facility_tb WHERE guestHouseCode=#{guestHouseCode}")
	public void deleteFacilities(int guestHouseCode);
	public void deleteRooms(List<Integer> roomCodeList);
	@Delete("DELETE FROM guestHouse_tb WHERE guestHouseCode=#{guestHouseCode}")
	public void deleteGuestHouse(int guestHouseCode);
	
	
	//회원예약내역
	public List<BookingDto> getGuestBookingList(@Param("hostCode") int hostCode, @Param("cri") BoardCriteria cri);
	//회원예약내역_페이징
	public int getGuestBookingListCount(int hostCode);
	
	@Update("UPDATE booking_tb SET bookingStatus='숙박완료', bookingEnd=now() WHERE bookingCode=#{bookingCode}")
	public void modifyBookingStatusToCheckout(int bookingCode);
	
	//회원리뷰관리
	@Select("SELECT r.*, v.guestHouseCode, v.guestHouseName, m.id AS writer FROM review_tb r, bkrmghmatching_view v, member_tb m"
			+ " WHERE r.bookingCode=v.bookingCode AND v.memberCode=m.memberCode AND v.hostCode=#{hostCode}")
	public List<ReviewDto> getGuestReviewList(int hostCode);
}
