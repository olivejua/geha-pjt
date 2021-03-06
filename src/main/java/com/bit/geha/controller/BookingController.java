package com.bit.geha.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bit.geha.dao.BookingDao;
import com.bit.geha.dto.BookingDto;
import com.bit.geha.dto.RoomDto;
import com.bit.geha.service.MemberService;

import lombok.extern.java.Log;

@Controller
@RequestMapping(value="/booking")
@Log
public class BookingController {
	
	@Autowired
	BookingDao bookingDao;
	
	@Autowired
	MemberService memberService;
	
	@RequestMapping("/bookingPage")
	public void loadBookingPage(Model model, int roomCode
			, @RequestParam(value="bookingStart") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date bookingStart
			, @RequestParam(value="bookingEnd") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date bookingEnd
			, int bookingNumber, HttpSession session, Authentication auth) {
		log.info("loadBookingPage()");
		RoomDto roomDto = bookingDao.getRoom(roomCode);
		String guestHouseName = bookingDao.getGuestHouseNameByGuestHouseCode(roomDto.getGuestHouseCode());

		//로그인 계정 가져오기
		memberService.getSession(auth,session);
		int memberCode=((Integer) session.getAttribute("memberCode")).intValue();
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("bookingStart", bookingStart);
		map.put("bookingEnd", bookingEnd);
		map.put("roomDto", roomDto);
		map.put("guestHouseName", guestHouseName);
		map.put("bookingNumber", bookingNumber);
		map.put("memberCode", memberCode);
		
		model.addAttribute("appliedBookingInfo", map);
	}
	
	@RequestMapping(value="/bookingComplete", method=RequestMethod.POST)
	public String bookingComplete(BookingDto bookingDto) {
		log.info("bookingComplete()");
		
		bookingDao.addBooking(bookingDto);
		int bookingCode = bookingDto.getBookingCode();
		
		return "redirect:/booking/bookingDetail?bookingCode="+bookingCode;
	}
	
	@RequestMapping("/bookingDetail")
	public void bookingDetail(int bookingCode, Model model) {
		log.info("bookingDetail()");
		
		BookingDto bookingDto = bookingDao.getBooking(bookingCode);
		bookingDto.setGuestHouseName(bookingDao.getGuestHouseNameByBookingCode(bookingCode));
		bookingDto.setRoomName(bookingDao.getRoomNameByBookingCode(bookingCode));
		
		model.addAttribute("roomDto", bookingDao.getRoom(bookingDto.getRoomCode()));
		model.addAttribute("bookingDto", bookingDto);
	}
}
