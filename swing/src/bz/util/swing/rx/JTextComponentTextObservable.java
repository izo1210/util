package bz.util.swing.rx;

import bz.util.java.Cast;
import java.util.EventObject;
import javax.swing.text.JTextComponent;
import lombok.NonNull;

public class JTextComponentTextObservable extends InputObservable<JTextComponent, String>
{
  public JTextComponentTextObservable(@NonNull JTextComponent view, @NonNull ListenerFactory publishWhen)
  {
    super(view,
        publishWhen,
        JTextComponentTextObservable::getText,
        JTextComponentTextObservable::setText
    );
  }

  private static String getText(EventObject event)
  {
    return Cast.toOptional(event.getSource(), JTextComponent.class)
        .map(textField->textField.getText())
        .orElseThrow();
  }

  private static void setText(JTextComponent view, String text)
  {
    view.setText(text);
  }




}

