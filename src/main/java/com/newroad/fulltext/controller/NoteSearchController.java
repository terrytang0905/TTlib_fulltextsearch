package com.newroad.fulltext.controller;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.newroad.fulltext.filter.TokenAuthFilter;
import com.newroad.fulltext.service.SearchServiceIf;
import com.newroad.fulltext.util.FullTextSearchConstants;
import com.newroad.fulltext.util.FullTextSearchException;
import com.newroad.util.StringHelper;
import com.newroad.util.encoding.StringEncodeUtil;
import com.newroad.util.apiresult.ApiReturnObjectUtil;


/**
 * @info Search Rest Controller
 * @author tangzj1
 * @date Aug 26, 2013
 * @version
 */
@Controller
@RequestMapping("/v{apiVersion}")
public class NoteSearchController {

  private static Logger logger = LoggerFactory.getLogger(NoteSearchController.class);

  @Autowired
  private SearchServiceIf searchService;

  /**
   * Search
   * 
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
  @RequestMapping(value = "/search")
  public @ResponseBody
  String search(HttpServletRequest request, @PathVariable String apiVersion) throws Exception {
    // Must set the correct CharacterEncoding before getting request parameters.
    request.setCharacterEncoding("UTF-8");
    try {
      String query = request.getParameter(FullTextSearchConstants.QUERY);
      if (query == null) {
        throw new FullTextSearchException("Search query keyword is null!");
      }
      // Need to set Encoding in tomcat config file manually.
      String encoding = StringEncodeUtil.getEncoding(query);
      logger.debug("search info encoding:" + encoding);
      if (!"UTF-8".equals(encoding)) {
        query = StringEncodeUtil.parse2UTF(query);
      }
      logger.info("Search info:" + query);

      String userId = "";
      JSONObject sessionUser = TokenAuthFilter.getCurrent() == null ? null : TokenAuthFilter.getCurrent();
      if (sessionUser != null) {
        userId = (String) sessionUser.get("uid");
      } else {
        throw new FullTextSearchException("Search user info is null!");
      }

      JSONObject param = StringHelper.getRequestEntity(request);
      Integer startIndex = (Integer) param.get("start");
      Integer size = (Integer) param.get("size");
      return searchService.search(userId, query, startIndex, size).toString();
    } catch (Exception e) {
      logger.error("Search Exception:", e);
      return ApiReturnObjectUtil.getReturnObjFromException(e).toString();
    }
  }
}
