/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.rest.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 */
public class NewChat {
  
    @NotNull(message = "{required}")
    public long roomId;
    
    @NotNull(message = "{required}")
    @Size(message = "{required}", min = 1)
    public String content;
}
