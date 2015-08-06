package com.goree.api.controller;


import com.goree.api.domain.Note;
import com.goree.api.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/group/note")
public class NoteController {
    @Autowired
    private NoteService noteService;

    /**
     * @api
     * @apiGroup
     * @apiDescription
     */
    public Note findNoteById(long id) {
        return noteService.findNoteById(id);
    }

    /**
     * @api
     * @apiGroup
     * @apiDescription
     */
    public Note writeNote(Note note) {
        return noteService.writeNote(note);
    }
}
