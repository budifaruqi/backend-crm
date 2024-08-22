package com.example.test.command.followUp;

import com.example.test.command.model.followUp.GetAllFollowUpCommandRequest;
import com.example.test.web.model.response.followUp.GetFollowUpWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllFollowUpCommand extends Command<GetAllFollowUpCommandRequest, Page<GetFollowUpWebResponse>> {}
