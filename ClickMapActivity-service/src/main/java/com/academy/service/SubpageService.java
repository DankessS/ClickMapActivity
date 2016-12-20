package com.academy.service;

import com.academy.cache.UserCache;
import com.academy.model.ChartData;
import com.academy.model.ChartResponseData;
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
import org.springframework.http.*;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Created by Daniel Palonek on 2016-08-19.
 */
@Service
@Transactional
public class SubpageService extends AbstractService<Subpage, SubpageDTO, SubpageRepo, SubpageMapper> {

    private static final Logger LOGGER = org.apache.log4j.LogManager.getLogger(SubpageService.class);

    @Autowired
    UserCache cache;

    @Autowired
    PointsService pointsService;

    public boolean saveSubpage(String name, MultipartFile file, RedirectAttributes redAttr) {
        WebsiteDTO websiteDTO = (WebsiteDTO) cache.getRequestedWebsite();
        if (!file.isEmpty() && websiteDTO != null) {
            try {
                File f = new File("ClickMapActivity-web/src/main/resources/images/" + websiteDTO.getId());
                if (!f.exists())
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
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
                redAttr.addFlashAttribute("message", "Could not upload subpage. Please try again.");
                return false;
            }
        }
        redAttr.addFlashAttribute("message", "Could not upload subpage. Please try again.");
        return false;
    }

    public boolean captureSubpage(String subpageUrl, RedirectAttributes redAttrs) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<byte[]> response = restTemplate.exchange(
                "http://api.screenshotmachine.com/?key=573ab4&size=F&format=PNG&url=" + subpageUrl,
                HttpMethod.GET, entity, byte[].class, "1");

        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                Files.write(Paths.get("ClickMapActivity-web/src/main/resources/images/test.png"), response.getBody());
                return true;
            } catch (IOException e) {
                LOGGER.warn(e.getMessage());
                redAttrs.addFlashAttribute("message", "Could not upload subpage. Please try again.");
                return false;
            }
        }
        return false;
    }

    public Iterable<SubpageDTO> getSubgapesForWebsiteId(final Long websiteId) {
        return mapper.convertToDTO(repo.getByWebsiteId(websiteId));
    }

    public SubpageDTO getByNameAndWebsiteId(String name, Long websiteId) {
        return mapper.convertToDTO(repo.findByNameAndWebsiteId(name, websiteId));
    }

    public void getImage(String name, String dateFromChain, String dateToChain, HttpServletResponse response) {
        final long start = System.currentTimeMillis();
        final WebsiteDTO website = (WebsiteDTO) cache.getRequestedWebsite();
        if (website == null) {
            return;
        }
        try {
            final LocalDateTime dateFrom = LocalDateTime.parse(dateFromChain.replace(" ", "T"));
            final LocalDateTime dateTo = LocalDateTime.parse(dateToChain.replace(" ", "T"));
            File f = new File("ClickMapActivity-web/src/main/resources/images/" + website.getId() + "/" + name);
            BufferedImage img = ImageIO.read(f);
            ByteArrayOutputStream outStr = new ByteArrayOutputStream();
            final SubpageDTO subpage = mapper.convertToDTO(repo.findByNameAndWebsiteId(name, website.getId()));
            Collection<ActivityDTO> activities = (Collection) cache.getSubpageActivities(subpage.getId());
            List<PointsDTO> points = new ArrayList<>();
            if (activities != null) {
                activities.stream()
                        .filter(a -> a.getDate().isAfter(dateFrom) && a.getDate().isBefore(dateTo))
                        .forEach(a -> points.addAll((Collection) cache.getActivityPoints(a.getId())));
            }
            int[][] clickMatrix = ImageConverter.makeClickMatrix(points, img.getWidth(), img.getHeight());
            img = ImageConverter.fillPointsMap(img, clickMatrix);
            ImageIO.write(img, "png", outStr);
            InputStream inputStream = new ByteArrayInputStream(outStr.toByteArray());
            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
            final long end = System.currentTimeMillis();
            LOGGER.info("Fetched image in: " + (end - start) + "milis");
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
        }
    }

    public Iterable<ChartResponseData> getChartData(String dateFromChain, String dateToChain, String granulation, String name) {
        final WebsiteDTO website = (WebsiteDTO) cache.getRequestedWebsite();
        if (website == null) {
            return Collections.EMPTY_LIST;
        }
        final SubpageDTO subpage = mapper.convertToDTO(repo.findByNameAndWebsiteId(name, website.getId()));
        if (subpage == null) {
            return Collections.EMPTY_LIST;
        }
        if ("day".equals(granulation)) {
            return getChartResponseDaily(dateFromChain, dateToChain, subpage);
        } else if ("hour".equals(granulation)) {
            return getChartResponseHourly(dateFromChain, dateToChain, subpage);
        }
        return Collections.EMPTY_LIST;
    }

    private Iterable<ChartResponseData> getChartResponseHourly(String dateFromChain, String dateToChain, SubpageDTO subpage) {
        Collection<ActivityDTO> activities = (Collection) cache.getSubpageActivities(subpage.getId());
        final LocalDateTime dateFrom = LocalDateTime.parse(dateFromChain.substring(0, dateFromChain.length() - 6), DateTimeFormatter.ofPattern("yyyy-MM-dd kk"));
        final LocalDateTime dateTo = LocalDateTime.parse(dateToChain.substring(0, dateFromChain.length() - 6), DateTimeFormatter.ofPattern("yyyy-MM-dd kk"));
        Map<LocalDateTime, Long> periodOccurances = activities.stream()
                .filter(a -> a.getDate().isAfter(dateFrom) && a.getDate().isBefore(dateTo))
                .map(a -> LocalDateTime.parse(a.getDate().toString().replace("T", " ").substring(0, a.getDate().toString().length() - (a.getDate().toString().length() > 16 ? 6 : 3)), DateTimeFormatter.ofPattern("yyyy-MM-dd kk")))
                .collect(Collectors.groupingBy(a -> a, Collectors.counting()));
        long hoursCount = ChronoUnit.HOURS.between(dateFrom, dateTo);
        List<ChartData> values = new LinkedList<>();
        for (long i = 0; i < hoursCount + 1; i++) {
            String incrementedDate = dateFrom.plusHours(i).toString();
            LocalDateTime parsedDate = magicDateParse(incrementedDate);
            values.add(new ChartData(parsedDate.toString(), 0));
        }
        for (long i = 0; i < values.size(); i++) {
            String incrementedDate = dateFrom.plusHours(i).toString();
            LocalDateTime parsedDate = magicDateParse(incrementedDate);
            if (periodOccurances.get(parsedDate) != null) {
                values.get(Math.toIntExact(i)).setX(incrementedDate.substring(incrementedDate.length() - 5, incrementedDate.length()));
                values.get(Math.toIntExact(i)).setY(periodOccurances.get(parsedDate));
            } else {
                values.get(Math.toIntExact(i)).setX(incrementedDate.substring(incrementedDate.length() - 5, incrementedDate.length()));
            }
        }
        return Collections.singletonList((new ChartResponseData("Clicks", values)));
    }

    private LocalDateTime magicDateParse(String dateToParse) {
        return LocalDateTime.parse(dateToParse.replace("T", " ").substring(0, dateToParse.length() - (dateToParse.length() > 16 ? 6 : 3)), DateTimeFormatter.ofPattern("yyyy-MM-dd kk"));
    }

    private Iterable<ChartResponseData> getChartResponseDaily(String dateFromChain, String dateToChain, SubpageDTO subpage) {
        Collection<ActivityDTO> activities = (Collection) cache.getSubpageActivities(subpage.getId());
        final LocalDateTime dateFrom = LocalDateTime.parse(dateFromChain.replace(" ", "T"));
        final LocalDateTime dateTo = LocalDateTime.parse(dateToChain.replace(" ", "T"));
        Map<LocalDate, Long> periodOccurances = activities.stream()
                .filter(a -> a.getDate().isAfter(dateFrom) && a.getDate().isBefore(dateTo))
                .collect(Collectors.groupingBy(a -> a.getDate().toLocalDate(), Collectors.counting()));
        long daysCount = ChronoUnit.DAYS.between(dateFrom, dateTo);
        List<ChartData> values = new LinkedList<>();
        for (long i = 0; i < daysCount + 1; i++) {
            values.add(new ChartData(dateFrom.plusDays(i).toLocalDate().toString(), 0));
        }
        values.add(new ChartData(dateTo.toLocalDate().toString(), 0));
        for (long i = 0; i < values.size(); i++) {
            final LocalDate date = dateFrom.plusDays(i).toLocalDate();
            if (periodOccurances.get(date) != null) {
                values.get(Math.toIntExact(i)).setY(periodOccurances.get(date));
            }
        }
        return Collections.singletonList((new ChartResponseData("Clicks", values)));
    }

    public boolean delete(final String name) {
        final Long websiteId = ((WebsiteDTO) cache.getRequestedWebsite()).getId();
        try {
            File file = new File("ClickMapActivity-web/src/main/resources/images/" + websiteId + "/" + name);
            if (!file.delete()) {
                return false;
            }
            repo.deleteByNameAndWebsiteId(name, websiteId);
            cache.setWebsiteSubpages(websiteId,
                    StreamSupport.stream(getSubgapesForWebsiteId(websiteId).spliterator(), true)
                            .collect(Collectors.toMap(SubpageDTO::getId, Function.identity()))
            );
            return true;
        } catch (Exception e) {
            LOGGER.warn(e.getMessage());
            return false;
        }
    }

    public boolean checkIfExists(String name) {
        final Long websiteId = ((WebsiteDTO) cache.getRequestedWebsite()).getId();
        return StreamSupport.stream(cache.getWebsiteSubpages(websiteId).spliterator(), false)
                .filter(w -> ((SubpageDTO) w).getName().equalsIgnoreCase(name))
                .findAny().isPresent();
    }

}
