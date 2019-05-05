package uoc.pec2.tecnologia.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import uoc.pec2.tecnologia.twitter.TwitterService;
import uoc.pec2.tecnologia.twitter.functions.FilterEnum;
import uoc.pec2.tecnologia.web.beans.Word;

@RestController
@Api(value = "Users microservice" )
public class ApiController {

	@Autowired
	private TwitterService twitterService;
	
    @GetMapping("/start/{filter}/{value}")
    @ApiOperation(tags = "Spark Operations", value = "Start")
    public void start(
    		@PathVariable(value = "filter") String filter,
    		@PathVariable(value = "value") String value) {
    	
        this.twitterService.start(FilterEnum.valueOf(filter.toUpperCase()), value);
    }
    
    @GetMapping("/words/{filter}")
    public List<Word> getWords(
    		@PathVariable(value = "filter") String filter) {
    	return this.twitterService.getWords(FilterEnum.valueOf(filter.toUpperCase()));
    }
}
