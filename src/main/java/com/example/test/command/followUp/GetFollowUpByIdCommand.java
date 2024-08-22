package com.example.test.command.followUp;

import com.example.test.command.model.followUp.GetFollowUpByIdCommandRequest;
import com.example.test.web.model.response.followUp.GetFollowUpWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetFollowUpByIdCommand extends Command<GetFollowUpByIdCommandRequest, GetFollowUpWebResponse> {}
