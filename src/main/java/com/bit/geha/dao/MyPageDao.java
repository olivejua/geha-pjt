package com.bit.geha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bit.geha.criteria.AdminPageCriteria;
import com.bit.geha.dto.BookingDto;
import com.bit.geha.dto.FileDto;
import com.bit.geha.dto.LikeDto;
import com.bit.geha.dto.ReviewDto;

@Mapper
public interface MyPageDao {
	
	@Select("SELECT b.*, v.guestHouseName, v.roomName FROM booking_tb b, bkrmghmatching_view v "
			+ "WHERE b.memberCode = #{memberCode} AND b.bookingCode=v.bookingCode "
			+ "order by b.bookingCode desc limit #{cri.pageStart}, #{cri.perPageNum}")
	public List<BookingDto> getBookingListByMemberCode(@Param("cri")AdminPageCriteria cri,int memberCode);
	
	@Update("UPDATE booking_tb SET bookingStatus=#{bookingStatus} WHERE bookingCode=#{bookingCode}")
	public void modifyBookingStatus(int bookingCode, String bookingStatus);
	
	@Select("SELECT b.bookingCode FROM booking_tb b, review_tb r "
			+ "WHERE b.bookingStatus = '숙박완료' AND b.memberCode=#{memberCode} AND b.bookingCode=r.bookingCode")
	public List<Integer> getReviewListByMemberCode(int memberCode);
	
	@Select("SELECT memberName FROM member_tb WHERE memberCode=#{memberCode}")
	public String getMemberName(int memberCode);
	
	@Insert("INSERT INTO review_tb (bookingCode, rating, writingDate, title, content) VALUES (#{bookingCode}, #{rating}, now(), #{title}, #{content})")
	public void addReview(ReviewDto reviewDto);
	
	@Select("SELECT guestHouseCode FROM bkrmghmatching_view WHERE bookingCode=#{bookingCode}")
	public int getGuestHouseCode(int bookingCode);
	
	public void calculateAvgRating(int guestHouseCode);
	
	public void modifyNameEtc(String id,String memberName,String businessLicense,String gender);
	
	public void modifyInfo(String id,String memberName, String password, String businessLicense,String gender);
	
	public List<ReviewDto> getReviewList(@Param("cri")AdminPageCriteria cri, int memberCode);
	
	public void modifyReview(ReviewDto reviewDto);
	
	public void deleteReview(int reviewNo);
	
	public List<LikeDto> myLike(@Param("cri")AdminPageCriteria cri,int memberCode);
	
	public void deleteLike(int memberCode,List<Integer> deleteLikeList);
	
	public int getLikeTotal(int memberCode);
	
	public int getReviewTotal(int memberCode);
	
	public int getBookingListTotal(int memberCode);
	
	public List<FileDto> getFileList(int guestHouseCode);
}
