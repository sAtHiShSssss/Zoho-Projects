package com.sathish.controller;

import com.opensymphony.xwork2.ActionSupport;
import com.sathish.dao.AdminService;

public class GetSuggestionsAction extends ActionSupport
{
    private final AdminService adminService = new AdminService();
    public void authorNames()
    {

        adminService.getAuthorsName();
    }
    public void genreNames()
    {
        adminService.getGenresName();
    }
}
