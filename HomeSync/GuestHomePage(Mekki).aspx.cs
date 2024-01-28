using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using System.Web.Configuration;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace HomeSyncAppFinal
{
    public partial class GuestHomePage : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }

        protected void CreateEventBtn_Click(object sender, EventArgs e)
        {
            Response.Redirect("CreateEventPage(Mekki).aspx");
        }

        protected void ViewEventBtn_Click(object sender, EventArgs e)
        {
            Response.Redirect("ViewEventPage(Mekki).aspx");
        }

        protected void AssignUserBtn_Click(object sender, EventArgs e)
        {
            Response.Redirect("AssignUserPage(Mekki).aspx");
        }

        protected void UninviteUserBtn_Click(object sender, EventArgs e)
        {
            Response.Redirect("UninviteUserPage(Mekki).aspx");
        }

        protected void RemoveEventBtn(object sender, EventArgs e)
        {
            Response.Redirect("RemoveEventPage(Mekki).aspx");
        }
    }
}