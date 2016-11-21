package com.academy.service;

import com.academy.cache.UserCache;
import com.academy.model.dao.Subpage;
import com.academy.model.dto.ActivityDTO;
import com.academy.model.dto.PointsDTO;
import com.academy.model.dto.SubpageDTO;
import com.academy.model.dto.WebsiteDTO;
import com.academy.repo.SubpageRepo;
import com.academy.service.mappers.SubpageMapper;
import com.academy.service.tools.ImageConverter;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
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
                SubpageDTO subpageDTO = new SubpageDTO();
                BufferedImage img = ImageIO.read(file.getInputStream());
                img = ImageConverter.grayScale(img);
                ImageIO.write(img, "png", f);
                subpageDTO.setName(name);
                subpageDTO.setResX(img.getWidth());
                subpageDTO.setResY(img.getHeight());
                subpageDTO.setWebsiteId(((WebsiteDTO) cache.getRequestedWebsite()).getId());
                subpageDTO = mapper.convertToDTO(repo.save(mapper.convertToDAO(subpageDTO)));
                cache.updateWebsiteSubpage(websiteDTO.getId(), subpageDTO.getId(), subpageDTO);
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

    public SubpageDTO getByNameAndWebsiteId(String name, Long websiteId) {
        return mapper.convertToDTO(repo.findByNameAndWebsiteId(name,websiteId));
    }

    public void getImage(String name, String dateFromChain, String dateToChain, HttpServletResponse response) {
        final WebsiteDTO website = (WebsiteDTO) cache.getRequestedWebsite();
        if(website == null) {
            return;
        }
        try {
            final LocalDateTime dateFrom = LocalDateTime.parse(dateFromChain.replace(" ", "T"));
            final LocalDateTime dateTo = LocalDateTime.parse(dateToChain.replace(" ", "T"));
            File f = new File("ClickMapActivity-web/src/main/resources/images/" + website.getId() + "/" + name);
            BufferedImage img = ImageIO.read(f);
            ByteArrayOutputStream outStr = new ByteArrayOutputStream();
            final SubpageDTO subpage = mapper.convertToDTO(repo.findByNameAndWebsiteId(name,website.getId()));
            Collection<ActivityDTO> activities = (Collection)cache.getSubpageActivities(subpage.getId());
            List<PointsDTO> points = new ArrayList<>();
            if(activities != null) {
                activities.stream()
                        .filter(a-> a.getDate().isAfter(dateFrom) && a.getDate().isBefore(dateTo))
                        .forEach(a -> points.addAll((Collection) cache.getActivityPoints(a.getId())));
            }
            int [][] clickMatrix = ImageConverter.makeClickMatrix(points,img.getWidth(),img.getHeight());
            img = ImageConverter.fillPointsMap(img,clickMatrix);
            ImageIO.write(img,"png",outStr);
            InputStream inputStream = new ByteArrayInputStream(outStr.toByteArray());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public boolean delete(final String name) {
        final Long websiteId = ((WebsiteDTO)cache.getRequestedWebsite()).getId();
        try{
            File file = new File("ClickMapActivity-web/src/main/resources/images/" + websiteId + "/" + name);
            if(!file.delete()) {
                return false;
            }
            repo.deleteByNameAndWebsiteId(name,websiteId);
            cache.setWebsiteSubpages(websiteId,
                    StreamSupport.stream(getSubgapesForWebsiteId(websiteId).spliterator(), true)
                            .collect(Collectors.toMap(SubpageDTO::getId, Function.identity()))
            );
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
