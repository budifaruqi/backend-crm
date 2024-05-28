package com.example.test.command.partner;

import com.example.test.command.model.partner.GetAllPartnerCommandRequest;
import com.example.test.web.model.response.partner.GetPartnerWebResponse;
import com.solusinegeri.command.reactive.Command;
import org.springframework.data.domain.Page;

public interface GetAllPartnerCommand extends Command<GetAllPartnerCommandRequest, Page<GetPartnerWebResponse>> {}
