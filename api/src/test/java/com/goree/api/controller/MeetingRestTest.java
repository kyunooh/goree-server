package com.goree.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goree.api.domain.Group;
import com.goree.api.domain.Meeting;
import com.goree.api.domain.Member;
import com.goree.api.domain.Place;
import com.goree.api.service.GroupService;
import com.goree.api.service.MemberService;
import com.goree.api.util.RestTestWithDBUnit;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class MeetingRestTest extends RestTestWithDBUnit {

    @Autowired
    private GroupService groupService;

    @Autowired
    private MemberService memberService;

    @Override
    public String getDatasetFilePath() {
        return "src/test/resources/testdataset/meeting_test_setup.xml";
    }

    @Before
    public void setUp() {
        super.setUp();
    }

    @Test
    public void createMeeting() throws Exception {
        Meeting expected = new Meeting();
        expected.setTitle("createMeetingBBBBB");
        expected.setDescription("etsnsaeitetaenharsithaneitnatre");
        Group groupOfExpected = groupService.findGroupByName("meeting_test");
        expected.setGroup(groupOfExpected);
        Date meetingDate = Date.from(
                LocalDate.of(
                        2030, Month.JULY, 28).atTime(0, 0)
                         .atZone(ZoneId.systemDefault()).toInstant());
        expected.setDate(meetingDate);
        Member promoter = memberService.findMemberAll().get(0);
        expected.setPromoter(promoter);
        Place place = new Place();
        place.setName("Place the createMeeting");
        place.setAddress("Adddddddddddreeeesss");
        place.setXCoordinate(new BigDecimal("36.017194"));
        place.setYCoordinate(new BigDecimal("128.6978236"));
        expected.setPlace(place);


        String json = new ObjectMapper().writeValueAsString(expected);

        performSet(post(MeetingController.CREATE_MEETING_URL), json)
            .andExpect(jsonPath("$.title").value(expected.getTitle()))
            .andExpect(jsonPath("$.description").value(expected.getDescription()))
            .andExpect(jsonPath("$.group.name").value(expected.getGroup().getName()))

            //placeID의 값은 Long 이지만 Json의 ID 값을 Integer 형으로 인식하기때문에 1을 하드코딩...
            .andExpect(jsonPath("$.promoter.id").value(1))
            .andExpect(jsonPath("$.place.name").value(place.getName()))
            .andExpect(jsonPath("$.place.address").value(place.getAddress()))
            //coordinate 값은 BigDecimal이지만 Json의 ID 값을 double 형식으로 인식하기때문에 좌표를 하드코딩...
            .andExpect(jsonPath("$.place.xCoordinate").value(36.017194))
            .andExpect(jsonPath("$.place.yCoordinate").value(128.6978236));


    }

    @Test
    public void findMeetingById() throws Exception {
        int meetingId = 1;

        performSet(get(MeetingController.FIND_MEETING_BY_ID_URL, meetingId))
            .andExpect(jsonPath("$.id").value(meetingId))
            .andExpect(jsonPath("$.date", is(notNullValue())))
            .andExpect(jsonPath("$.place", is(notNullValue())))
            .andExpect(jsonPath("$.promoter", is(notNullValue())))
            .andExpect(jsonPath("$.title", is(notNullValue())))
            .andExpect(jsonPath("$.group", is(notNullValue())));
    }

    @Test
    public void findMeetingsByGroupId() throws Exception{
        long groupId = 1;

        performSet(get(MeetingController.FIND_MEETINGS_BY_GROUP_ID_URL, groupId))
            .andExpect(jsonPath("$",hasSize(4)))

            //groupId의 값은 Long 이지만 Json의 ID 값을 Integer 형으로 인식하기때문에 1을 하드코딩...
            .andExpect(jsonPath("$.[0].group.id").value(1));
    }

    @Test
    public void findMeetingsByGroups() throws Exception {
        String json = "[{\"id\":1},{\"id\":2}]";

        performSet(post(MeetingController.FIND_MEETINGS_BY_GROUPS_URL), json)
            .andExpect(jsonPath("$", hasSize(4)));

    }

}
