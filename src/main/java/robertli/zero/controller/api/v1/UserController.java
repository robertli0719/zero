/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import robertli.zero.dto.PagingModal;
import robertli.zero.dto.QueryResult;
import robertli.zero.dto.user.GeneralUserDto;
import robertli.zero.service.GeneralUserService;

/**
 *
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/users")
public class UserController {

    @Resource
    private GeneralUserService generalUserService;

    @RequestMapping(method = RequestMethod.GET)
    public List<GeneralUserDto> getGeneralUserList(@RequestAttribute PagingModal pagingModal) {
        final int offset = pagingModal.getOffset();
        final int limit = pagingModal.getLimit();
        final QueryResult queryResult = generalUserService.getGeneralUserList(offset, limit);
        final int count = queryResult.getCount();
        pagingModal.placeHeaders(count);
        return queryResult.getResultList();
    }

    @RequestMapping(path = "searcher", method = RequestMethod.GET)
    public List<GeneralUserDto> search(@RequestAttribute PagingModal pagingModal, String words) {
        final int offset = pagingModal.getOffset();
        final int limit = pagingModal.getLimit();
        final QueryResult queryResult = generalUserService.searchUser(words, offset, limit);
        final int count = queryResult.getCount();
        pagingModal.placeHeaders(count);
        return queryResult.getResultList();
    }

}
