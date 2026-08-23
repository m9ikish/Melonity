package ru.melonity.fabric.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface ILocalPlayer {
   boolean serverSprintState();

   /**
    * Overwrites the client's record of the sprint state the server last saw. Anything that hand-
    * sends a {@code ClientCommandC2SPacket} sprint toggle has to call this, otherwise vanilla's
    * {@code sendSprintingPacket} still sees a state change and emits a second, redundant packet.
    */
   void setServerSprintState(boolean state);

   float getXRotLast();

   boolean canStartSprint();
}
