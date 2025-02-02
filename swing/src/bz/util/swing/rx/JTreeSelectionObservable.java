package bz.util.swing.rx;

import bz.util.java.Cast;
import bz.util.swing.util.DefaultTreeNode;
import bz.util.swing.util.Listener;
import java.util.EventObject;
import java.util.Optional;
import java.util.function.Consumer;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class JTreeSelectionObservable<N extends TreeNode> extends InputObservable<JTree, N>
{

  public JTreeSelectionObservable(JTree view)
  {
    super(view,
        JTreeSelectionObservable::treeSelectionListener,
        JTreeSelectionObservable::getSelectedNode,
        JTreeSelectionObservable::setSelectedNode
    );
  }

  private static Listener treeSelectionListener(Object eventSource, Consumer<EventObject> handler)
  {
    return Listener.treeSelectionChanged(eventSource, event->handler.accept(event));
  }

  protected static <N1 extends TreeNode> N1 getSelectedNode(EventObject event)
  {
    TreeSelectionEvent treeSelectionEvent=Cast.toOptional(event, TreeSelectionEvent.class).get();
    Object selectedNode=Optional.ofNullable(treeSelectionEvent.getNewLeadSelectionPath())
        .map(selectionPath->selectionPath.getLastPathComponent())
        .orElse(null);
    return (N1)selectedNode;
  }

  protected static void setSelectedNode(JTree tree, TreeNode selectedNode)
  {
    TreePath treePath=Optional.ofNullable(selectedNode)
        .map(node->DefaultTreeNode.treePathOf(node))
        .orElse(null);
    tree.setSelectionPath(treePath);
  }

}
