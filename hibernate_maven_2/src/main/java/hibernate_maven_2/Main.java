package hibernate_maven_2;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import hibernate_maven_2.model.Personel;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Main extends JFrame {

	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtAd;
	private JTextField txtSoyad;
	private JTextField txtMaas;
	private JTable table;

	private EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnitName");

	private EntityManager manager = factory.createEntityManager();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	List<Personel> personelListesi = null;
	private JButton btnGncelle;
	private JButton btnSil;

	private void personelListele() {

		Query query = manager.createQuery("SELECT p FROM Personel as p ORDER BY p.id"); // JPQL
		// Java Persistence Query Language

		personelListesi = query.getResultList();

		DefaultTableModel model = new DefaultTableModel();

		model.addColumn("Id");
		model.addColumn("Ad");
		model.addColumn("Soyad");
		model.addColumn("Maaş");

		Object[] row = new Object[4];

		int size = personelListesi.size();

		for (int i = 0; i < size; i++) {
			row[0] = personelListesi.get(i).getId();
			row[1] = personelListesi.get(i).getAd();
			row[2] = personelListesi.get(i).getSoyad();
			row[3] = personelListesi.get(i).getMaas();

			model.addRow(row);
		}

		table.setModel(model);
	}

	/**
	 * Create the frame.
	 */
	public Main() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Id");
		lblNewLabel.setBounds(24, 40, 46, 14);
		contentPane.add(lblNewLabel);

		JLabel lblAd = new JLabel("Ad");
		lblAd.setBounds(24, 65, 46, 14);
		contentPane.add(lblAd);

		JLabel lblSoyad = new JLabel("Soyad");
		lblSoyad.setBounds(24, 90, 46, 14);
		contentPane.add(lblSoyad);

		JLabel lblMaa = new JLabel("Maaş");
		lblMaa.setBounds(24, 115, 46, 14);
		contentPane.add(lblMaa);

		txtId = new JTextField();
		txtId.setHorizontalAlignment(SwingConstants.RIGHT);
		txtId.setText("0");
		txtId.setEditable(false);
		txtId.setBounds(80, 37, 86, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);

		txtAd = new JTextField();
		txtAd.setBounds(80, 62, 86, 20);
		contentPane.add(txtAd);
		txtAd.setColumns(10);

		txtSoyad = new JTextField();
		txtSoyad.setColumns(10);
		txtSoyad.setBounds(80, 87, 86, 20);
		contentPane.add(txtSoyad);

		txtMaas = new JTextField();
		txtMaas.setHorizontalAlignment(SwingConstants.RIGHT);
		txtMaas.setColumns(10);
		txtMaas.setBounds(80, 112, 86, 20);
		contentPane.add(txtMaas);

		JButton btnKaydet = new JButton("Ekle");
		btnKaydet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				EntityTransaction transaction = manager.getTransaction();

				Personel personel = new Personel(txtAd.getText(), txtSoyad.getText(),
						new BigDecimal(txtMaas.getText()));

				transaction.begin();
				manager.persist(personel); // persist : insert
				transaction.commit();

				txtId.setText(String.valueOf(personel.getId()));

				personelListele();

				manager.close();

				JOptionPane.showMessageDialog(btnKaydet, "Yeni personel kaydedildi.");
			}
		});
		btnKaydet.setBounds(77, 148, 89, 23);
		contentPane.add(btnKaydet);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 207, 414, 205);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setBounds(0, 0, 1, 1);
		scrollPane.setViewportView(table);

		btnGncelle = new JButton("Güncelle");
		btnGncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Personel p = manager.find(Personel.class, Integer.valueOf(txtId.getText()));

				p.setAd(txtAd.getText());
				p.setSoyad(txtSoyad.getText());
				p.setMaas(new BigDecimal(txtMaas.getText()));

				EntityTransaction t = manager.getTransaction();

				t.begin();
				manager.merge(p); // update : merge
				t.commit();

				personelListele();

				manager.close();

			}
		});
		btnGncelle.setBounds(181, 148, 89, 23);
		contentPane.add(btnGncelle);
		
		btnSil = new JButton("Sil");
		btnSil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Personel p = 
						manager.getReference(Personel.class,Integer.valueOf(txtId.getText())); //find(Personel.class, Integer.valueOf(txtId.getText()));

				EntityTransaction t = manager.getTransaction();

				t.begin();
				manager.remove(p); // remove : delete
				t.commit();

				personelListele();

				manager.close();
			}
		});
		btnSil.setBounds(300, 148, 89, 23);
		contentPane.add(btnSil);

		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent event) {
				int selectedIndex = table.getSelectedRow();

				if (selectedIndex != -1) {

					Personel secilenPersonel = personelListesi.get(selectedIndex);

					txtId.setText("" + secilenPersonel.getId());
					txtAd.setText(secilenPersonel.getAd());
					txtSoyad.setText(secilenPersonel.getSoyad());
					txtMaas.setText(secilenPersonel.getMaas().toString());
				}
			}
		});

		personelListele();
	}
}
