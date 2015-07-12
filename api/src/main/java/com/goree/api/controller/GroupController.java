package com.goree.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goree.api.domain.Group;
import com.goree.api.domain.Member;
import com.goree.api.service.GroupService;

@RestController
public class GroupController {
    
    @Autowired
    private GroupService groupService;
    
    @RequestMapping(value="/groups/joined", method=RequestMethod.GET)
    public List<Group> groupsJoined(Member member){
        return groupService.findRegistedGroupsByMember(member);
    }
}
