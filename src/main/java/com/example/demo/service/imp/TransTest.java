package com.example.demo.service.imp;

import java.util.List;
import java.util.Random;

import javax.validation.Valid;
import javax.validation.constraints.Max;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import com.example.demo.dao.datastruct.SellRecord;
import com.example.demo.dao.datastruct.User;
import com.example.demo.dao.datastruct.VisitRecord;
import com.example.demo.dao.interf.GoodsRepository;
import com.example.demo.dao.interf.OrderListRepository;
import com.example.demo.dao.interf.SellRecordRepository;
import com.example.demo.dao.interf.UserRepository;
import com.example.demo.dao.interf.VisitRecordRepository;
import com.example.demo.service.interf.UserService;
import com.example.demo.util.ValidateUtil;

import lombok.extern.slf4j.Slf4j;
import java.sql.Timestamp;

@Slf4j
@Service
public class TransTest {
	@Autowired
	UserService os;
	
	//@Transactional(rollbackFor=Exception.class)
	public void transTest()
	{
		
		//throw new RuntimeException();
	}
}
