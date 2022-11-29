package org.neg5.controllers;

import com.google.inject.Inject;
import org.neg5.core.EnumSerializer;
import neg5.domain.api.enums.MatchResult;
import neg5.domain.api.enums.TossupAnswerType;

public class Neg5EnumController extends AbstractJsonController {

    private final EnumSerializer enumSerializer;

    @Inject
    public Neg5EnumController(EnumSerializer enumSerializer) {
        this.enumSerializer = enumSerializer;
        addEnums();
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api";
    }

    @Override
    public void registerRoutes() {
        get("/*/enums", (request, response) -> enumSerializer.serialize());
    }

    private void addEnums() {
        enumSerializer
                .add(TossupAnswerType.class)
                .add(MatchResult.class);
    }
}
