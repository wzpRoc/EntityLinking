package org.ailab.wimfra.util;

import org.jdom.Document;
import org.jdom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * User: Lu Tingming
 * Date: 2011-10-8
 * Time: 20:56:03
 * Desc:
 */
public class TreeNode {
    public static int nextId = 1;

    public final Type defaultNodeType = Type.node;

    public static enum Type{node, clazz, instance}

    private static HashSet<String> jdomAttributes;

    public String id;
    public String code;
    public String name;
    public String desc;
    public String url;
    public String path;
    public Type type;
    public String parentId;
    public int eventType;

    public int level;
    public int noChildren = 0;
    public int noOffsprings = 0;

    public TreeNode parent;
    public ArrayList<TreeNode> children;

    static {
        setJDOMAttributes("name", "desc");
    }

    public static void setJDOMAttributes(String... attributes) {
        jdomAttributes = new HashSet<String>();
        jdomAttributes.addAll(Arrays.asList(attributes));
    }

    public TreeNode() {
    }

    public TreeNode(String code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public static TreeNode createRoot(String code, String name, String desc) {
        TreeNode node = createRoot();
        node.code = code;
        node.name = name;
        node.desc = desc;
        node.level = 0;

        return node;
    }


    public static TreeNode createRoot() {
        TreeNode root = new TreeNode();
        root.parent = null;
        root.level = 0;
        // todo
        root.id = String.valueOf(getNextId());
        root.code = "0";
        root.name = "全部";
        root.parentId = "0";

        return root;
    }


    /**
     * 只加入列表，不操作数据库中的数据
     * @param child
     */
    public void addChildToChildren(TreeNode child) {
        if (children == null) {
            children = new ArrayList<TreeNode>();
        }

        children.add(child);
        child.parent = this;
    }

    public void addChild(TreeNode child) {
        addChildToChildren(child);

        child.level = this.level + 1;

        // todo
        child.id = String.valueOf(getNextId());
        child.code = this.code + "_" + children.size();
        // child.name = "全部";
        child.parentId = this.id;

        onChildrenAdding();
    }

    private void onChildrenAdding() {
        this.noChildren++;
        onOffspringAdding();
    }

    public void onOffspringAdding() {
        this.noOffsprings++;
        if (this.parent != null) {
            parent.onOffspringAdding();
        }
    }

    public void print(StringBuffer sb, INodePrinter nodePrinter) {
        nodePrinter.printNode(sb, this);
        if (children != null) {
            for (TreeNode child : children) {
                child.print(sb, nodePrinter);
            }
        }
    }

    public void print(StringBuffer sb, INodePrinter nodePrinter, int maxLevel) {
        if (maxLevel >= 0 && level > maxLevel) {
            return;
        }

        nodePrinter.printNode(sb, this);
        if (children != null) {
            for (TreeNode child : children) {
                child.print(sb, nodePrinter, maxLevel);
            }
        }
    }


    public Document toJDOMDocument() {
        Element root = toJDOMElement();
        return new Document(root);
    }

    public Element toJDOMElement() {
        String nodeName;
        if(type==null){
            type = defaultNodeType;
        }
        switch(type){
            case clazz:
                nodeName = "class";
                break;
            case instance:
                nodeName = "instance";
                break;
            default:
                nodeName = "node";
                break;
        }
        Element ele = new Element(nodeName);

        if (jdomAttributes.contains("code")) {
            ele.setAttribute("code", code);
        }

        if (jdomAttributes.contains("name")) {
            ele.setAttribute("name", name);
        }

        if (jdomAttributes.contains("noChildren")) {
            if (noChildren > 0) {
                ele.setAttribute("children", String.valueOf(noChildren));
            }
        }

        if (jdomAttributes.contains("noOffsprings")) {
            if (noOffsprings > 0) {
                ele.setAttribute("offsprings", String.valueOf(noOffsprings));
            }
        }

        if (jdomAttributes.contains("desc")) {
            if (desc != null && !"".equals(desc)) {
                ele.setAttribute("desc", desc);
            }
        }

        if (children != null && children.size() > 0) {
            for (TreeNode child : children) {
                Element childEle = child.toJDOMElement();
                ele.addContent(childEle);
            }
        }

        return ele;
    }


    public static int getNextId(){
        final int temp_nextId = nextId;
        nextId++;
        return temp_nextId;
    }

    public ArrayList<TreeNode> toList_dfs(){
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();

        this.toList_dfs(list);

        return list;
    }


    public ArrayList<TreeNode> toList_dfs(ArrayList<TreeNode> list){
        list.add(this);
        if(this.children!=null && this.children.size()>0){
            for(TreeNode child:children){
                child.toList_dfs(list);
            }
        }

        return list;
    }

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the children
	 */
	public ArrayList<TreeNode> getChildren() {
		return children;
	}

	/**
	 * @param children the children to set
	 */
	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(Type type) {
		this.type = type;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the eventType
	 */
	public int getEventType() {
		return eventType;
	}

	/**
	 * @param eventType the eventType to set
	 */
	public void setEventType(int eventType) {
		this.eventType = eventType;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @return the noChildren
	 */
	public int getNoChildren() {
		return noChildren;
	}

	/**
	 * @param noChildren the noChildren to set
	 */
	public void setNoChildren(int noChildren) {
		this.noChildren = noChildren;
	}

	/**
	 * @return the noOffsprings
	 */
	public int getNoOffsprings() {
		return noOffsprings;
	}

	/**
	 * @param noOffsprings the noOffsprings to set
	 */
	public void setNoOffsprings(int noOffsprings) {
		this.noOffsprings = noOffsprings;
	}

	/**
	 * @return the parent
	 */
	public TreeNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(TreeNode parent) {
		this.parent = parent;
	}

	/**
	 * @param nextId the nextId to set
	 */
	public static void setNextId(int nextId) {
		TreeNode.nextId = nextId;
	}


}