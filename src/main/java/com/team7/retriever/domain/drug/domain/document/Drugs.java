package com.team7.retriever.domain.drug.domain.document;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "drugs")
public class Drugs {

    @Id
    private String _id;
    private String drugName;
    private String drugType;
    private String drugEnName;

}
