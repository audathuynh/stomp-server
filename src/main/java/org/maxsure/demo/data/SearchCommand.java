package org.maxsure.demo.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 * @author Dat Huynh
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchCommand extends BaseCommand {

    private String keyword;
    private int offset;
    private int pageSize;
}
