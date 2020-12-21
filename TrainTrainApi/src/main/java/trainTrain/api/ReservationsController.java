package trainTrain.api;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.traintrain.WebTicketManager;

@RestController
public class ReservationsController {

	@RequestMapping(method = RequestMethod.POST, value = "api/reservations")
	public String update(@RequestBody final RequestDto requestDto) throws IOException, InterruptedException {

		final WebTicketManager webTicketManager = new WebTicketManager();

		return webTicketManager.reserve(requestDto.getTrain_id(), requestDto.getNumber_of_seats());
	}

	@RequestMapping(method = RequestMethod.GET, value = "api/value")
	public String get() {
		return "Reservations";
	}
}
