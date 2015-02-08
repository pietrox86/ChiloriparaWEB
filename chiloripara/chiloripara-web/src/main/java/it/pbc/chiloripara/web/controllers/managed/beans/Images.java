package it.pbc.chiloripara.web.controllers.managed.beans;

import it.pbc.chiloripara.services.interfaces.IArtigianoService;
import it.pbc.chiloripara.services.interfaces.IPostService;
import it.pbc.chiloripara.services.interfaces.IPropertiesService;
import it.pbc.chiloripara.web.model.entities.Artigiano;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("images")
@Scope("session")
public class Images {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

//   @Autowired
//    private PostDAO service;
//   @Autowired
//   private ArtigianoDAO artDao;
	@Autowired
	private IPostService postService;
	@Autowired
	private IArtigianoService artService;
   @Autowired
   private IPropertiesService properties;

    public StreamedContent getImage() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();

        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("imageId");
         //   Post post= postService.getPost(new Long(id));
           // postService.save(post);
            
            return new DefaultStreamedContent(new ByteArrayInputStream(postService.getPost(new Long(id)).getImg()));
            
        }
    }
    
    public StreamedContent getIcon() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        logger.info("ritorno lo StreamedContent "+context.getCurrentPhaseId());
        if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
            // So, we're rendering the view. Return a stub StreamedContent so that it will generate right URL.
            return new DefaultStreamedContent();
        }
        else {
            // So, browser is requesting the image. Return a real StreamedContent with the image bytes.
            String id = context.getExternalContext().getRequestParameterMap().get("iconId");
            Artigiano art= artService.get(new Long(id));
           
            logger.info("ritorno lo StreamedContent "+art.getId());      
            return new DefaultStreamedContent(new ByteArrayInputStream(art.getIcon()));
            
        }
    }

}