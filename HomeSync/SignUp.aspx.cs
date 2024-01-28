using Microsoft.SqlServer.Server;
using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Diagnostics;
using System.Diagnostics.Eventing.Reader;
using System.Globalization;
using System.Linq;
using System.Web;
using System.Web.Configuration;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace HomeSyncWebApp
{
    public partial class SignUp : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {

        }
        protected void signUp(object sender, EventArgs e)

        {

            MessageLabel.Text = "";
            ExceptionLabel.Text = "";

            //new connection
            string connstr = WebConfigurationManager.ConnectionStrings["HomeSync"].ToString();
            SqlConnection conn = new SqlConnection(connstr);


            //parameters
            string usertype = TypeBox1.Text;
            string email = EmailBox.Text;
            string password = PasswordBox.Text;
            string first_name = FirstNameBox.Text;
            string last_name = LastNameBox.Text;
            DateTime birth_date;

           

            //Handling Insertion errors
            //DateTime birth_date = Convert.ToDateTime(BirthDateBox.Text);
            if (usertype.Length > 20)
            {
                ExceptionLabel.Text += "Exceeded Number of characters (20) (in UserType)" + "<br />";
                return;
            }

            else if (usertype.Length == 0)
            {
                ExceptionLabel.Text += "User Type was not entered, Please try again! " + "<br />";
                return;
            }

            else if (email.Length > 20)
            {
                ExceptionLabel.Text += "Exceeded Number of characters (20) (in E-Mail)" + "<br />";
                return;
            }

            else if (email.Length == 0)
            {
                ExceptionLabel.Text += "The Email was not entered, Please try again! " + "<br />";
                return;
            }

            else if (password.Length > 10)
            { ExceptionLabel.Text += "Exceeded Number of characters (10) (in Password)" + "<br />"; return; }

            else if (password.Length == 0)
            { ExceptionLabel.Text += "The Password was not entered,Please try again! " + "<br />"; return; }

            else if (first_name.Length > 20)
            { ExceptionLabel.Text += "Exceeded Number of characters (20) (in First Name)" + "<br />"; return; }

            else if (first_name.Length == 0)
            { ExceptionLabel.Text += "The First Name was not entered,Please try again! " + "<br />"; return; }

            else if (last_name.Length > 20)
            { ExceptionLabel.Text += "Exceeded Number of characters (20) (in Last Name)" + "<br />"; return; }

            else if (last_name.Length == 0)
            { ExceptionLabel.Text += "The Last Name was not entered,Please try again! " + "<br />"; return; }

            else if (!DateTime.TryParseExact(BirthDateBox.Text, "yyyy-MM-dd", CultureInfo.InvariantCulture, DateTimeStyles.None, out birth_date))
            { ExceptionLabel.Text += "ERROR : Incorrect Date Format (in Birth Date" + "<br />"; return; }



            SqlCommand UserRegister = new SqlCommand("UserRegister", conn);
            UserRegister.CommandType = CommandType.StoredProcedure;
            UserRegister.Parameters.Add(new SqlParameter("@usertype", usertype));
            UserRegister.Parameters.Add(new SqlParameter("@email", email));
            UserRegister.Parameters.Add(new SqlParameter("@first_name", first_name));
            UserRegister.Parameters.Add(new SqlParameter("@last_name", last_name));
            UserRegister.Parameters.Add(new SqlParameter("@birth_date", birth_date));
            UserRegister.Parameters.Add(new SqlParameter("@password", password));
            SqlParameter user_id = new SqlParameter("@user_id", SqlDbType.Int);
            user_id.Direction = ParameterDirection.Output;
            UserRegister.Parameters.Add(user_id);

            try
            {
                conn.Open();
                UserRegister.ExecuteNonQuery();
                Session["signUpId"] = user_id.Value;

                if (user_id.Value.ToString().Equals("-1"))
                {
                    MessageLabel.Text = "A user already has this E-Mail.Please try Again!";
                }
                else
                {
                    
                    MessageLabel.Text = ("Sign-Up successful! YOUR USER ID IS : " + user_id.Value.ToString());
                    if (usertype.Equals("Admin"))
                        Response.Redirect("AdminSignUp.aspx");
                   else if (usertype.Equals("Guest"))
                        Response.Redirect("GuestSignUp.aspx");
                }
            }

            catch (SqlException sqlEx)
            {

                ExceptionLabel.Text += "ERROR : " + sqlEx.Message + "<br />";

            }
            catch (Exception ex)
            {
                ExceptionLabel.Text += "ERROR : " + ex.Message + "<br />";
            }
            finally
            {
                conn.Close();
            }
        }
    }
}
   