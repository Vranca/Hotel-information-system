package utility;

import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.DefaultFormatter;

import net.miginfocom.swing.MigLayout;

public class ValueField<V> extends JPanel
{
	private static final long serialVersionUID = 1L;

	private Class<?> valueClass = null;
	private V value = null;
	private Object fkValue = null;
	private JLabel valueName = null;
	private JComponent valueField = null;
	private JButton valueButton = null;
	private boolean isFk = false;

	public ValueField(V value, Class<?> valueClass, String valueName, boolean isFk, Object fkValue)
	{
		this.value = value;
		this.fkValue = fkValue;
		this.valueClass = valueClass;
		this.valueName = new JLabel(valueName);
		this.isFk = isFk;
		this.valueField = getComponentByClass();
		if (isFk)
		{
			valueButton = new JButton("...");
			valueButton.setMargin(new Insets(1, 3, 1, 3));
		}
	}

	@SuppressWarnings("unchecked")
	private JComponent getComponentByClass()
	{
		JComponent component = null;

		if (isFk)
		{
			component = new JFormattedTextField(value);
			((JFormattedTextField) component).setEnabled(false);
		} else if (valueClass == Integer.class)
		{
			component = new FormattedTextField(NumberFormat.getIntegerInstance(Locale.getDefault()));
			if (value != null)
				((FormattedTextField) component).setValue(value);
		} else if (valueClass == Double.class)
		{
			component = new FormattedTextField(NumberFormat.getInstance(Locale.getDefault()));
			if (value != null)
				((FormattedTextField) component).setValue(value);
		} else if (valueClass == Timestamp.class)
		{
			component = new FormattedTextField(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, Locale.getDefault()));
			if (value != null)
				((FormattedTextField) component).setValue(value);
		} else if (valueClass == Boolean.class)
		{
			component = new JComboBox<Boolean>();
			((JComboBox<Boolean>) component).addItem((Boolean) value);
			((JComboBox<Boolean>) component).addItem(!((Boolean) value));
		} else if (valueClass == byte[].class)
		{
			component = new JPanel(new MigLayout());
			JLabel img;
			JButton imgChooser = new JButton("...");
			imgChooser.getInsets().set(0, 2, 0, 2);

			ImageIcon imageIcon;
			if (value != null)
			{
				imageIcon = new ImageIcon((byte[]) value);
				img = new JLabel(imageIcon);
			} else
			{
				img = new JLabel();
			}
			component.add(img, "align left, pushx, growx, pushy, growy");
			component.add(imgChooser, "align right, pushx");
		} else
		{
			component = new FormattedTextField(new DefaultFormatter());
			if (value != null)
				((FormattedTextField) component).setValue(value);

		}

		return component;
	}

	public final V getValue()
	{
		return value;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public final V getRealValue()
	{
		if (valueField instanceof ValueField.FormattedTextField)
		{
			return (V) ((ValueField.FormattedTextField) valueField).getValue();
		} else if (valueField instanceof JComboBox)
		{
			return (V) ((JComboBox) valueField).getSelectedItem();
		} else if (valueClass == byte[].class)
		{
			if (value == null)
			{
				byte[] nullArr = { 0 };
				return (V) nullArr;
			} else if (((byte[]) value)[0] == 0)
			{
				byte[] nullArr = { 0 };
				return (V) nullArr;
			}

			try
			{
				JLabel label = (JLabel) valueField.getComponent(0);
				Icon img = label.getIcon();
				BufferedImage bi = new BufferedImage(img.getIconWidth(), img.getIconHeight(), BufferedImage.TYPE_INT_ARGB);

				Graphics g = bi.createGraphics();
				img.paintIcon(null, g, 0, 0);
				g.dispose();

				ByteArrayOutputStream os = new ByteArrayOutputStream();
				ImageIO.write(bi, "jpg", os);
				InputStream fis = new ByteArrayInputStream(os.toByteArray());
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				for (int readNum; (readNum = fis.read(buf)) != -1;)
				{
					bos.write(buf, 0, readNum);
					System.out.println("read " + readNum + " bytes,");
				}
				byte[] bytes = bos.toByteArray();

				return (V) bytes;
			} catch (Exception e)
			{
				e.printStackTrace();
				return value;
			}
		} else
		{
			return value;
		}
	}

	@SuppressWarnings("unchecked")
	public final void setValue(Object value)
	{
		this.value = (V) value;
		((JFormattedTextField) valueField).setValue(this.value);
	}

	public final JLabel getValueName()
	{
		return valueName;
	}

	public final JComponent getValueField()
	{
		return valueField;
	}

	public final JButton getValueButton()
	{
		return valueButton;
	}

	public final boolean isFk()
	{
		return isFk;
	}

	public final Object getFkValue()
	{
		return fkValue;
	}

	public final void setFkValue(Object fkValue)
	{
		this.fkValue = fkValue;
	}

	private class FormattedTextField extends JFormattedTextField
	{
		private static final long serialVersionUID = 1L;

		public FormattedTextField(NumberFormat integerInstance)
		{
			super(integerInstance);
		}

		public FormattedTextField(DateFormat dateTimeInstance)
		{
			super(dateTimeInstance);
		}

		public FormattedTextField(DefaultFormatter defaultFormatter)
		{
			super(defaultFormatter);
		}

		@Override
		protected void processFocusEvent(FocusEvent e)
		{
			String stringValue = getText();

			super.processFocusEvent(e);

			if ((stringValue == null || stringValue.length() == 0) && getValue() != null)
				setValue(null);

		}
	}

}
