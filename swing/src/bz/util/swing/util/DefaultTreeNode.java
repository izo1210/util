package bz.util.swing.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class DefaultTreeNode<T extends MutableTreeNode> implements MutableTreeNode
{
  protected T parent;
  protected final List<T> children=new ArrayList<>();
  protected Object userObject;
  
  public DefaultTreeNode(T parent)
  {
    setParent(parent);
  }
  
  @Override
  public void insert(MutableTreeNode child, int index)
  {
    children.add(index, (T)child);
  }

  @Override
  public void remove(int index)
  {
    children.remove(index);
  }

  @Override
  public void remove(MutableTreeNode node)
  {
    children.remove(node);
  }

  @Override
  public void setUserObject(Object object)
  {
    this.userObject=userObject;
  }

  @Override
  public void removeFromParent()
  {
    getParent().remove(this);
    setParent(null);
  }

  @Override
  public void setParent(MutableTreeNode newParent)
  {
    parent=(T)newParent;
  }

  @Override
  public T getChildAt(int childIndex)
  {
    return children.get(childIndex);
  }

  @Override
  public int getChildCount()
  {
    return children.size();
  }

  @Override
  public T getParent()
  {
    return parent;
  }

  @Override
  public int getIndex(TreeNode node)
  {
    return children.indexOf(node);
  }

  @Override
  public boolean getAllowsChildren()
  {
    return true;
  }

  @Override
  public boolean isLeaf()
  {
    return children.isEmpty();
  }

  @Override
  public Enumeration<? extends TreeNode> children()
  {
    return Collections.enumeration(children);
  }

  public TreePath treePath()
  {
    return treePathOf(this);
  }

  public static <T1 extends TreeNode> List<T1> hierarchy(List<T1> list, T1 node)
  {
    if(node==null) return null;
    list.addFirst(node);
    hierarchy(list, (T1)node.getParent());
    return list;
  }

  public static TreePath treePathOf(TreeNode node)
  {
    List<TreeNode> list=new ArrayList<>();
    hierarchy(list, node);
    Object[] objects=list.toArray();
    TreePath treePath=new TreePath(objects);
    return treePath;
  }


}
