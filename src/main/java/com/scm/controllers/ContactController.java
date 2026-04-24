package com.scm.controllers;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.scm.entities.Contact;
import com.scm.entities.User;
import com.scm.forms.ContactForm;
import com.scm.forms.ContactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.Helper;
import com.scm.helpers.Message;
import com.scm.helpers.MessageType;
import com.scm.services.ContactService;
import com.scm.services.ImageService;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private ImageService imageService;

    @Autowired
    private UserService userService;

    /* ================= ADD CONTACT ================= */

    @GetMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        contactForm.setFavorite(true);
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @PostMapping("/add")
    public String saveContact(
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult result,
            Authentication authentication,
            HttpSession session) {

        if (result.hasErrors()) {
            session.setAttribute("message",
                    Message.builder()
                            .content("Please correct the errors")
                            .type(MessageType.red)
                            .build());
            return "user/add_contact";
        }

        User user = userService.getUserByEmail(
                Helper.getEmailOfLoggedInUser(authentication));

        Contact contact = new Contact();
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());
        contact.setUser(user);

        // Image upload (safe)
        if (contactForm.getContactImage() != null &&
                !contactForm.getContactImage().isEmpty()) {

            try {
                String publicId = UUID.randomUUID().toString();
                String imageUrl = imageService.uploadImage(
                        contactForm.getContactImage(), publicId);

                contact.setPicture(imageUrl);
                contact.setCloudinaryImagePublicId(publicId);
            } catch (Exception e) {
                logger.error("Image upload failed", e);
            }
        }

        contactService.save(contact);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact added successfully")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts";
    }

    /* ================= VIEW CONTACTS ================= */

    @GetMapping
    public String viewContacts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        User user = userService.getUserByEmail(
                Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "user/contacts";
    }

    /* ================= SEARCH CONTACTS ================= */

    @PostMapping("/search")
    public String searchHandler(
            @ModelAttribute ContactSearchForm form,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = AppConstants.PAGE_SIZE + "") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction,
            Model model,
            Authentication authentication) {

        User user = userService.getUserByEmail(
                Helper.getEmailOfLoggedInUser(authentication));

        Page<Contact> pageContact = switch (form.getField().toLowerCase()) {
            case "email" -> contactService.searchByEmail(
                    form.getValue(), size, page, sortBy, direction, user);
            case "phone" -> contactService.searchByPhoneNumber(
                    form.getValue(), size, page, sortBy, direction, user);
            default -> contactService.searchByName(
                    form.getValue(), size, page, sortBy, direction, user);
        };

        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", form);

        return "user/search";
    }

    /* ================= DELETE CONTACT (FIXED) ================= */

    @PostMapping("/delete/{contactId}")
    public String deleteContact(
            @PathVariable String contactId,
            HttpSession session) {

        contactService.delete(contactId);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact deleted successfully")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts";
    }

    /* ================= UPDATE CONTACT ================= */

    @GetMapping("/view/{contactId}")
    public String updateContactView(
            @PathVariable String contactId,
            Model model) {

        Contact contact = contactService.getById(contactId);

        ContactForm form = new ContactForm();
        form.setName(contact.getName());
        form.setEmail(contact.getEmail());
        form.setPhoneNumber(contact.getPhoneNumber());
        form.setAddress(contact.getAddress());
        form.setDescription(contact.getDescription());
        form.setFavorite(contact.isFavorite());
        form.setWebsiteLink(contact.getWebsiteLink());
        form.setLinkedInLink(contact.getLinkedInLink());
        form.setPicture(contact.getPicture());

        model.addAttribute("contactForm", form);
        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(
            @PathVariable String contactId,
            @Valid @ModelAttribute ContactForm contactForm,
            BindingResult result,
            HttpSession session) {

        if (result.hasErrors()) {
            return "user/update_contact_view";
        }

        Contact contact = contactService.getById(contactId);

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setAddress(contactForm.getAddress());
        contact.setDescription(contactForm.getDescription());
        contact.setFavorite(contactForm.isFavorite());
        contact.setWebsiteLink(contactForm.getWebsiteLink());
        contact.setLinkedInLink(contactForm.getLinkedInLink());

        if (contactForm.getContactImage() != null &&
                !contactForm.getContactImage().isEmpty()) {

            try {
                String publicId = UUID.randomUUID().toString();
                String imageUrl = imageService.uploadImage(
                        contactForm.getContactImage(), publicId);

                contact.setCloudinaryImagePublicId(publicId);
                contact.setPicture(imageUrl);
            } catch (Exception e) {
                logger.error("Image update failed", e);
            }
        }

        contactService.update(contact);

        session.setAttribute("message",
                Message.builder()
                        .content("Contact updated successfully")
                        .type(MessageType.green)
                        .build());

        return "redirect:/user/contacts/view/" + contactId;
    }
}
