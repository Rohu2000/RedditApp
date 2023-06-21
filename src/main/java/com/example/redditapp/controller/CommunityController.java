package com.example.redditapp.controller;

import com.example.redditapp.model.Community;
import com.example.redditapp.model.User;
import com.example.redditapp.service.CommunityService;
import com.example.redditapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Controller
public class CommunityController {
    @Autowired
    CommunityService communityService;
    @Autowired
    UserService userService;

    @GetMapping("/newcommunity")
    public String newCommunity(Model model){
        model.addAttribute("community", new Community());
        return "createcommunity";
    }

    @PostMapping("/savecommunity")
    public String saveCommunity(@ModelAttribute("community") Community community,
                                @RequestParam("communityType") String communityType,
                                BindingResult bindingResult,
                                Model model) {

        if (communityService.isCommunityNameExists(community.getCommunityName())) {
            model.addAttribute("message", true);
            return "redirect:/newcommunity";
        }
        Community savedCommunity = communityService.createCommunity(community,communityType);
        model.addAttribute("community",savedCommunity);
         return "community";
    }

    @GetMapping("/view-community/r/{communityName}")
    public String viewCommunity(@PathVariable("communityName") String communityName,Model model){
        Community community = communityService.findCommunityByCommunityName(communityName);
        model.addAttribute("community",community);
        return "community";
    }

    @GetMapping("/users/r")
    public String viewMembers(@RequestParam(value = "communityName") String communityName, Model model){
     Community community = communityService.findCommunityByCommunityName(communityName);
     model.addAttribute("community",community);
     return "viewmembers";

    }
   @GetMapping("/community/addModerator")
   public String addMemberToModerator(@RequestParam("userId") Long userId ,
                                      @RequestParam("communityName") String communityName,
                                      Model model)
   {

       Community community = communityService.findCommunityByCommunityName(communityName);
       User user = userService.getUserById(userId);
       communityService.addMemberToModerator(community, user);
       userService.addModeratedCommunity(user, community);
       model.addAttribute("community",community);
       return "redirect:/users/r?communityName="+communityName;

   }
   @GetMapping("/community/removeModerator")
   public String removeMemberFromModerator(@RequestParam("userId") Long userId ,
                                      @RequestParam("communityName") String communityName,
                                      Model model) {
       Community community = communityService.findCommunityByCommunityName(communityName);
       User user = userService.getUserById(userId);
       communityService.removeMemberFromModerator(community, user);
       userService.removeModeratedCommunity(user, community);
       model.addAttribute("community",community);
       return "redirect:/users/r?communityName="+communityName;

   }

    @GetMapping("/community/banUser")
    public String removeUserFromCommunity(@RequestParam("userId") Long userId,
                                          @RequestParam("communityName") String communityName,
                                          Model model) {

        Community community = communityService.findCommunityByCommunityName(communityName);
        User user = userService.getUserById(userId);
        communityService.removeUserFromCommunity(community, user);
        model.addAttribute("community", community);
        return "redirect:/users/r?communityName="+communityName;
    }

    @GetMapping("/updatecommunity/{id}")
    public Community updateCommunity(@PathVariable Long id, @RequestBody Community updatedCommunity) {
        return communityService.updateCommunity(id, updatedCommunity);
    }
    @GetMapping("/deletecommunity/{id}")
    public void deleteCommunity(@PathVariable Long id) {
        communityService.deleteCommunity(id);
    }
}
