package bz.util.swing.util;

import bz.util.java.NOX;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

public class Listener<T>
{
  private final Class<T> listenerClass;
  private final Set<Object> eventSources=new HashSet<>();
  private T listener;

  public Listener(Class<T> listenerClass, Set<Object> eventSources, T listener)
  {
    this.listenerClass=listenerClass;
    set(listener);
    eventSources.forEach(this::addTo);
    this.eventSources.addAll(eventSources);
  }

  public synchronized boolean removeFrom(Object eventSource)
  {
    if(listener==null) return false;
    if(eventSource==null) return false;
    Method removeListenerMethod=NOX.get(()->eventSource.getClass().getMethod("remove"+listenerClass.getSimpleName(), listenerClass));
    NOX.run(()->removeListenerMethod.invoke(eventSource, listener));
    return eventSources.remove(eventSource);
  }

  public synchronized boolean addTo(Object eventSource)
  {
    if(listener==null) return false;
    if(eventSource==null) return false;
    if(eventSources.contains(eventSource)) return false;
    eventSources.add(eventSource);
    Method addListenerMethod=NOX.get(()->eventSource.getClass().getMethod("add"+listenerClass.getSimpleName(), listenerClass));
    NOX.run(()->addListenerMethod.invoke(eventSource, listener));
    return true;
  }

  public synchronized void removeFromAll()
  {
    eventSources.forEach(this::removeFrom);
  }

  public final synchronized void set(T listener)
  {
    removeFromAll();
    if(listener==null) return;
    this.listener=listener;
    eventSources.forEach(this::addTo);
  }

  public T get()
  {
    return listener;
  }

  public Set<Object> eventSources()
  {
    return Collections.unmodifiableSet(eventSources);
  }

  //static helper methods
  
  public static <T> Consumer<T> filter(Predicate<T> condition, Consumer<T> handler)
  {
    return (T t)->{
      if(condition.test(t))
      {
        handler.accept(t);
      }
    };
  }

  public static boolean maskMatches(ActionEvent event, int mask)
  {
    int eventModifiers=event.getModifiers();
    int maskedEventModifiers=eventModifiers&mask;
    boolean maskMatches=maskedEventModifiers!=0;
    return maskMatches;
  }

  public static Consumer<ActionEvent> filter(int mask, Consumer<ActionEvent> handler)
  {
    return filter(e->maskMatches(e, mask), handler);
  }

  public static Set<Object> setOf(Object eventSource)
  {
    return eventSource==null ? Set.of() : Set.of(eventSource);
  }

  //static factory methods

  public static Listener<ActionListener> actionPerformed(Object eventSource, Consumer<ActionEvent> handler)
  {
    ActionListener listener=(ActionEvent e)->handler.accept(e);
    return new Listener<>(ActionListener.class, setOf(eventSource), listener);
  }

  public static Listener<MouseListener> mouseClicked(Object eventSource, Consumer<MouseEvent> handler)
  {
    MouseListener listener=new MouseAdapter()
    {
      @Override
      public void mouseClicked(MouseEvent e)
      {
        handler.accept(e);
      }
    };
    return new Listener<>(MouseListener.class, setOf(eventSource), listener);
  }

  public static Listener<FocusListener> focusGained(Object eventSource, Consumer<FocusEvent> handler)
  {
    FocusListener listener=new FocusAdapter()
    {
      @Override
      public void focusGained(FocusEvent e)
      {
        handler.accept(e);
      }
    };
    return new Listener<>(FocusListener.class, setOf(eventSource), listener);
  }

  public static Listener<FocusListener> focusLost(Object eventSource, Consumer<FocusEvent> handler)
  {
    FocusListener listener=new FocusAdapter()
    {
      @Override
      public void focusLost(FocusEvent e)
      {
        handler.accept(e);
      }
    };
    return new Listener<>(FocusListener.class, setOf(eventSource), listener);
  }

  public static Listener<ListSelectionListener> valueChanged(Object eventSource, Consumer<ListSelectionEvent> handler)
  {
    ListSelectionListener listener=(ListSelectionEvent e)->
    {
      if(!e.getValueIsAdjusting())
      {
        handler.accept(e);
      }
    };
    return new Listener<>(ListSelectionListener.class, setOf(eventSource), listener);
  }

  public static Listener<TreeSelectionListener> treeSelectionChanged(Object eventSource, Consumer<TreeSelectionEvent> handler)
  {
    TreeSelectionListener listener=(TreeSelectionEvent e)->handler.accept(e);
    return new Listener<>(TreeSelectionListener.class, setOf(eventSource), listener);
  }

}
