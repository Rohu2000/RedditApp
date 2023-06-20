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
import org.springframework.web.servlet.ModelAndView;

import java.util.HashSet;
import java.util.List;
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
        String message = null;
        String communityName = community.getCommunityName();
        if (communityService.isCommunityNameExists(communityName)) {
            model.addAttribute("message", true);
            return "redirect:/newcommunity";
        }
        Set<User> comm_moderators = community.getCommunityModerators();
        Set<User> comm_members = community.getCommunityMembers();
        Community savedCommunity = communityService.createCommunity(community,communityType);
        model.addAttribute("community",savedCommunity);
        model.addAttribute("moderators", comm_moderators);
        model.addAttribute("members", comm_members);
        return "community";
    }

    @GetMapping("/view-community/r/{communityName}")
    public String viewCommunity(@PathVariable("communityName") String communityName,Model model){
        Community community = communityService.findCommunity(communityName);
        model.addAttribute("community",community);
        return "community";
    }

    @GetMapping("/users/r")
    public String viewMembers(@RequestParam(value = "communityId") Long communityId, Model model){
     Community community = communityService.findCommunityById(communityId);
     model.addAttribute("community",community);
     return "viewmembers";

    }
   @GetMapping("/community/addModerator")
   public String addMemberToModerator(@RequestParam("userId") Long userId ,
                                      @RequestParam("communityId") Long communityId,
                                      Model model)
   {

       Community community = communityService.findCommunityById(communityId);
       User user = userService.getUserById(userId);
       Set<User> moderators = community.getCommunityModerators();
       moderators.add(user);
       community.setCommunityModerators(moderators);

       Set<Community> moderatedCommunities = new HashSet<>();
       moderatedCommunities.add(community);
       user.setCommunityModerators(moderatedCommunities);

       communityService.saveCommunity(community);
       userService.saveUser(user);
       model.addAttribute("community",community);
       return "redirect:/users/r?communityId="+communityId;

   }
   @GetMapping("/community/removeModerator")
   public String removeMemberToModerator(@RequestParam("userId") Long userId ,
                                      @RequestParam("communityId") Long communityId,
                                      Model model) {
       Community community = communityService.findCommunityById(communityId);
       User user = userService.getUserById(userId);
       Set<User> moderators = community.getCommunityModerators();
       moderators.remove(user);
       community.setCommunityModerators(moderators);

       Set<Community> moderatedCommunities = new HashSet<>();
       moderatedCommunities.remove(community);
       user.setCommunityModerators(moderatedCommunities);
       communityService.saveCommunity(community);
       userService.saveUser(user);
       model.addAttribute("community",community);
       return "redirect:/users/r?communityId="+communityId;

   }
    @GetMapping("/community/banUser")
    public String removeUserFromCommunity(@RequestParam("userId") Long userId,
                                          @RequestParam("communityId") Long communityId,
                                          Model model) {
        Community community = communityService.findCommunityById(communityId);
        User user = userService.getUserById(userId);
        community.getCommunityMembers().remove(user);
        community.getCommunityModerators().remove(user);
        communityService.saveCommunity(community);
        model.addAttribute("community", community);
        return "redirect:/users/r?communityId="+communityId;
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
