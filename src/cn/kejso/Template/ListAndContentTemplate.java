package cn.kejso.Template;

import cn.kejso.Template.ToolEntity.ContentConfig;
import cn.kejso.Template.ToolEntity.ListConfig;

//列表-内容模式模板
public class ListAndContentTemplate extends AbstractTemplate{
		private  ListConfig  listconfig;
		private  ContentConfig contentconfig;
		
		public ListConfig getListconfig() {
			return listconfig;
		}
		public void setListconfig(ListConfig listconfig) {
			this.listconfig = listconfig;
		}
		public ContentConfig getContentconfig() {
			return contentconfig;
		}
		public void setContentconfig(ContentConfig contentconfig) {
			this.contentconfig = contentconfig;
		}
}
