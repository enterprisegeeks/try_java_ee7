/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enterprisegeeks.websocket;

import java.util.Arrays;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 *
 * @author kentaro.maeda
 */
public class SignalDecoder implements Decoder.Text<Signal>{

    @Override
    public Signal decode(String message) throws DecodeException {
        return Signal.valueOf(message.toUpperCase());
    }

    @Override
    public boolean willDecode(String message) {
        return Arrays.stream(Signal.values()).anyMatch(
                en -> en.toString().equals(message.toUpperCase()));
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    
}
