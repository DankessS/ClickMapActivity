package com.academy.service;

import com.academy.cache.UserCache;
import com.academy.model.dao.Subpage;
import com.academy.model.dto.SubpageDTO;
import com.academy.model.dto.WebsiteDTO;
import com.academy.repo.SubpageRepo;
import com.academy.service.mappers.SubpageMapper;
import com.academy.service.tools.ImageConverter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
@Transactional
public class SubpageService extends AbstractService<Subpage,SubpageDTO,SubpageRepo,SubpageMapper> {

    private static final Logger LOGGER = org.apache.log4j.LogManager.getLogger(SubpageService.class);

    @Autowired
    UserCache cache;

    public boolean saveSubpage(String name, MultipartFile file, RedirectAttributes redAttr) {
        WebsiteDTO websiteDTO = (WebsiteDTO)cache.getRequestedWebsite();
        if(!file.isEmpty() && websiteDTO != null) {
            try{
                File f = new File("ClickMapActivity-web/src/main/resources/images/" + websiteDTO.getId());
                if(!f.exists())
                    f.mkdirs();
                f = new File("ClickMapActivity-web/src/main/resources/images/" + websiteDTO.getId() + "/" + name);
                BufferedOutputStream s = new BufferedOutputStream(new FileOutputStream(f));
                FileCopyUtils.copy(file.getInputStream(),s);
                s.close();
                BufferedImage img = ImageIO.read(f);
                final SubpageDTO subpageDTO = new SubpageDTO();
                subpageDTO.setName(name);
                subpageDTO.setResX(img.getWidth());
                subpageDTO.setResY(img.getHeight());
                subpageDTO.setWebsiteId(((WebsiteDTO) cache.getRequestedWebsite()).getId());
                repo.save(mapper.convertToDAO(subpageDTO));
                cache.setWebsiteSubpages(websiteDTO.getId(),mapper.convertToDTO(repo.getByWebsiteId(websiteDTO.getId())));
                redAttr.addAttribute("websiteUrl", websiteDTO.getUrl());
                return true;
            }catch(IOException e) {
                LOGGER.warn(e.getMessage());
                redAttr.addFlashAttribute("message", "Could not upload subpage. Please try again.");
                return false;
            }
        }
        redAttr.addFlashAttribute("message", "Could not upload subpage. Please try again.");
        return false;
    }

    public Iterable<SubpageDTO> getSubgapesForWebsiteId(final Long websiteId) {
        return mapper.convertToDTO(repo.getByWebsiteId(websiteId));
    }

    public boolean delete(final String name) {
        final Long websiteId = ((WebsiteDTO)cache.getRequestedWebsite()).getId();
        try{
            File file = new File("ClickMapActivity-web/src/main/resources/images/" + websiteId + "/" + name);
            if(!file.delete()) {
                return false;
            }
            repo.deleteByNameAndWebsiteId(name,websiteId);
            cache.setWebsiteSubpages(websiteId,getSubgapesForWebsiteId(websiteId));
            return true;
        } catch(Exception e) {
            LOGGER.warn(e.getMessage());
            return false;
        }
    }

    public boolean checkIfExists(String name) {
        final Long websiteId = ((WebsiteDTO)cache.getRequestedWebsite()).getId();
        return StreamSupport.stream(cache.getWebsiteSubpages(websiteId).spliterator(),false)
                .filter(w -> ((SubpageDTO)w).getName().equalsIgnoreCase(name))
                .findAny().isPresent();
    }

}
