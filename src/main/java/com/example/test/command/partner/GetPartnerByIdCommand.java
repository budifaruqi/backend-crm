package com.example.test.command.partner;

import com.example.test.command.model.partner.GetPartnerByIdCommandRequest;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import com.solusinegeri.command.reactive.Command;

public interface GetPartnerByIdCommand extends Command<GetPartnerByIdCommandRequest, GetPartnerWebResponse> {}
