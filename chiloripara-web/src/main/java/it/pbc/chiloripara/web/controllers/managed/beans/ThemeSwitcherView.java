package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.web.utls.Theme;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("tsw")
public class ThemeSwitcherView {
 
    private List<Theme> themes;
     
    @Autowired
    private ThemeService service;
 
    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
     
    public List<Theme> getThemes() {
        return themes;
    } 
 
    public void setService(ThemeService service) {
        this.service = service;
    }
}