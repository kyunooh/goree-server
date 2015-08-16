package com.goree.api.service;


import com.goree.api.domain.MeetingNote;
import com.goree.api.mapper.MeetingNoteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeetingNoteService {
    @Autowired
    private MeetingNoteMapper meetingNoteMapper;

    public MeetingNote writeMeetingNote(MeetingNote meetingNote) {
        meetingNoteMapper.insertMeetingNote(meetingNote);
        return findLastMeetingNoteByMeetingId(meetingNote.getMeeting().getId());
    }

    public MeetingNote findMeetingNoteById(long meetingNoteId) {
        return meetingNoteMapper.selectMeetingNoteById(meetingNoteId);
    }

    public MeetingNote findLastMeetingNoteByMeetingId(long meetingId) {
        return meetingNoteMapper.selectLastMeetingNoteByMeetingId(meetingId);
    }
}
