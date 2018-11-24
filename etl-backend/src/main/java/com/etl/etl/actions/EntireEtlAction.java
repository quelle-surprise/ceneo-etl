package com.etl.etl.actions;

import org.springframework.beans.factory.annotation.Autowired;

public class EntireEtlAction {

    @Autowired
    ExtractAction extractAction;
    @Autowired
    LoadAction loadAction;
    @Autowired
    TransformAction transformAction;

}
