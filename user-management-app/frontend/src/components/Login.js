import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { authService } from '../services/api';

function Login() {
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: '', // Changed from email to username
    password: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [showReset, setShowReset] = useState(false);
  const [resetData, setResetData] = useState({
    username: '',
    mobileNumber: '',
    newPassword: '',
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await authService.login(formData);
      if (response.success && response.data) {
        localStorage.setItem('user', JSON.stringify(response.data));
        navigate('/dashboard');
      }
    } catch (err) {
      setError(err.message || 'Login failed. Please check your credentials.');
    } finally {
      setLoading(false);
    }
  };

  const handleResetSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setLoading(true);

    try {
      const response = await authService.resetPassword(resetData);
      if (response.success) {
        alert('Password reset successfully! Please login with your new password.');
        setShowReset(false);
        setResetData({ username: '', mobileNumber: '', newPassword: '' });
      }
    } catch (err) {
      setError(err.message || 'Password reset failed.');
    } finally {
      setLoading(false);
    }
  };

  const handleResetChange = (e) => {
    setResetData({
      ...resetData,
      [e.target.name]: e.target.value,
    });
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        {!showReset ? (
          <>
            <h2>Welcome</h2>
            <p className="auth-subtitle">Login to your account</p>

            {error && <div className="error-message">{error}</div>}

            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="username">Username</label>
                <input
                  type="text"
                  id="username"
                  name="username"
                  value={formData.username}
                  onChange={handleChange}
                  required
                  placeholder="Enter your username"
                />
              </div>

              <div className="form-group">
                <label htmlFor="password">Password</label>
                <input
                  type="password"
                  id="password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                  required
                  placeholder="Enter your password"
                />
              </div>

              <button type="submit" className="btn-primary" disabled={loading}>
                {loading ? 'Logging in...' : 'Login'}
              </button>
            </form>

            <p className="auth-footer">
              <button onClick={() => setShowReset(true)} className="link-button">
                Forgot Password?
              </button>
            </p>

            <p className="auth-footer">
              Don't have an account?{' '}
              <button onClick={() => navigate('/register')} className="link-button">
                Register here
              </button>
            </p>
          </>
        ) : (
          <>
            <h2>Reset Password</h2>
            <p className="auth-subtitle">Enter your username and mobile number to reset password</p>

            {error && <div className="error-message">{error}</div>}

            <form onSubmit={handleResetSubmit}>
              <div className="form-group">
                <label htmlFor="username">Username</label>
                <input
                  type="text"
                  id="username"
                  name="username"
                  value={resetData.username}
                  onChange={handleResetChange}
                  required
                  placeholder="Enter your username"
                />
              </div>

              <div className="form-group">
                <label htmlFor="mobileNumber">Mobile Number</label>
                <input
                  type="tel"
                  id="mobileNumber"
                  name="mobileNumber"
                  value={resetData.mobileNumber}
                  onChange={handleResetChange}
                  required
                  pattern="[0-9]{10}"
                  placeholder="Enter 10-digit mobile number"
                />
              </div>

              <div className="form-group">
                <label htmlFor="newPassword">New Password</label>
                <input
                  type="password"
                  id="newPassword"
                  name="newPassword"
                  value={resetData.newPassword}
                  onChange={handleResetChange}
                  required
                  minLength="6"
                  placeholder="Enter new password"
                />
              </div>

              <button type="submit" className="btn-primary" disabled={loading}>
                {loading ? 'Resetting...' : 'Reset Password'}
              </button>
            </form>

            <p className="auth-footer">
              <button onClick={() => setShowReset(false)} className="link-button">
                Back to Login
              </button>
            </p>
          </>
        )}
      </div>
    </div>
  );
}

export default Login;

