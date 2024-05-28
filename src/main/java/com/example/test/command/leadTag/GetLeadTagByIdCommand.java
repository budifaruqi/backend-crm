package com.example.test.command.leadTag;

import com.example.test.command.model.leadTag.GetLeadTagByIdCommandRequest;
import com.example.test.web.model.response.leadTag.GetLeadTagWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetLeadTagByIdCommand extends Command<GetLeadTagByIdCommandRequest, GetLeadTagWebResponse> {}
