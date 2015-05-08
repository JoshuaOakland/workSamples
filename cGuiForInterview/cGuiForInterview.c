#include <gtk/gtk.h>

void destroy(GtkWidget *widget, gpointer data)
{
	gtk_main_quit();
}
void clicked(GtkWidget *widget, gpointer data)
{
	g_print("I wrote this in C, utilizing the Gtk api.\n");
}

void wrong(GtkWidget *widget, gpointer data)
{
	g_print("Oops you clicked the wrong button please try again!\n");
}

main (int argc, char *argv[])
{
	GtkWidget *window;
	GtkWidget *button;
	GtkWidget *button2;
	GtkWidget *container;
	gtk_init(&argc, &argv);

	window = gtk_window_new(GTK_WINDOW_TOPLEVEL);
	gtk_window_set_title(GTK_WINDOW(window), "Interview Window!");
	gtk_container_set_border_width(GTK_CONTAINER(window), 100);
	container = gtk_box_new(FALSE, 0);
	gtk_container_add(GTK_CONTAINER(window), container);
	button = gtk_button_new_with_label("Hire me!");
	gtk_box_pack_start(GTK_BOX(container), button, TRUE, TRUE, 0);
	g_signal_connect(G_OBJECT(button), "clicked", G_CALLBACK(clicked), NULL);
	button2 = gtk_button_new_with_label("Don't hire me!");
	gtk_box_pack_start(GTK_BOX(container), button2, TRUE, TRUE, 0);
	g_signal_connect(G_OBJECT(button2), "clicked", G_CALLBACK(wrong), NULL);
	g_signal_connect(G_OBJECT(window), "destroy", G_CALLBACK(destroy), NULL);

	gtk_widget_show(button);
	gtk_widget_show(button2);
	gtk_widget_show(container);
	gtk_widget_show(window);
	gtk_main();
	return 0;
}
