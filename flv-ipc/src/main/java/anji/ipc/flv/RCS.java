package anji.ipc.flv;

import anji.ipc.core.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RCS {

  @Autowired
  @Resource(name = "rcsChannel")
  private Channel channel;




}
